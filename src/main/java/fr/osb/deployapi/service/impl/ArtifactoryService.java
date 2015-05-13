package fr.osb.deployapi.service.impl;

import fr.osb.deployapi.model.*;
import fr.osb.deployapi.model.base.ModelWithUri;
import fr.osb.deployapi.service.RepositoryManagerService;
import fr.osb.deployapi.util.DeployableType;
import fr.osb.deployapi.util.Paths;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;

/**
 * Repository Manager service implementation based on Artifactory resource.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Service
public class ArtifactoryService implements RepositoryManagerService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactoryService.class);

    /**
     * REST template.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Repository manager API URL.
     */
    @Value("${repositoryManager.api}")
    private String repositoryManagerApi;

    /**
     * Repository manager authentication.
     */
    @Value("${repositoryManager.auth}")
    private String repositoryManagerAuth;

    /**
     * {@inheritDoc}
     */
    @Override
    public Builds getAllBuilds() {

        LOGGER.info("Retrieving all builds identifiers.");

        final ResponseEntity<Builds> response = restTemplate.exchange(Paths.p(repositoryManagerApi, "build"),
                HttpMethod.GET, HttpEntity.EMPTY, Builds.class);

        LOGGER.info("All builds: {}", response.getBody());

        return response.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildsNumbers getBuildNumbers(final String build) {

        if (StringUtils.isBlank(build)) {
            throw new IllegalArgumentException("Invalid build identifier.");
        }

        LOGGER.info("Retrieving build '{}' latest version available numbers.", build);

        final ResponseEntity<BuildsNumbers> response = restTemplate.exchange(Paths.p(repositoryManagerApi, "build", build),
                HttpMethod.GET, HttpEntity.EMPTY, BuildsNumbers.class);

        final BuildsNumbers buildsNumbers = response.getBody();

        Collections.sort(buildsNumbers.getBuildsNumbers());

        LOGGER.info("Build '{}' available numbers: {}", build, buildsNumbers);

        return buildsNumbers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildInfo getBuildInfo(final String build, final Integer number) {

        if (StringUtils.isBlank(build) || number == null) {
            throw new IllegalArgumentException("Invalid build identifier.");
        }

        LOGGER.info("Retrieving build '{}' #{} information.", build, number);

        final HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(repositoryManagerAuth.getBytes()));

        final ResponseEntity<BuildInfo> response = restTemplate.exchange(Paths.p(repositoryManagerApi, "build", build, number),
                HttpMethod.GET, new HttpEntity<Object>(header), BuildInfo.class);

        LOGGER.info("Build '{}' #{} information: {}", build, number, response.getBody());

        return response.getBody();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInfo getBuildArtifact(final String build, final Integer number) {

        LOGGER.info("Retrieving build '{}' #{} deployable artifact information.", build, number);

        // --
        // Retrieving build info.
        // --

        final BuildInfo buildInfo = getBuildInfo(build, number);

        if (CollectionUtils.isEmpty(buildInfo.getBuildInfo().getModules())) {
            throw new UnsupportedOperationException("No module for build '" + build + "' #" + number);
        }

        final BuildInfo.Module module = buildInfo.getBuildInfo().getModules().get(0);

        // --
        // Retrieving artifact SHA-1 code.
        // --

        String artifactSha1 = null;
        DeployableType deployableType = null;

        for (final BuildInfo.Artifact artifact : module.getArtifacts()) {
            if (DeployableType.isDeployableType(artifact.getType())) {
                artifactSha1 = artifact.getSha1();
                deployableType = DeployableType.fromString(artifact.getType());
                break;
            }
        }

        if (artifactSha1 == null) {
            throw new UnsupportedOperationException("No deployable artifact has been found for build '" + build + "' #" + number + ".");
        }

        // --
        // Retrieving artifact matching the SHA-1 code.
        // --

        final ResponseEntity<GavSearchResults> response = restTemplate.exchange(Paths.p(repositoryManagerApi, "search", "gavc?g={g}&a={a}&v={v}"),
                HttpMethod.GET, HttpEntity.EMPTY, GavSearchResults.class, module.getGav().asUrlVariables());

        final GavSearchResults searchResult = response.getBody();

        LOGGER.debug("Build '{}' #{} GAV search results: {}", build, number, searchResult);

        FileInfo artifact = null;

        for (final ModelWithUri hasUri : searchResult.getResults()) {

            if (!DeployableType.isType(hasUri.getUri(), deployableType)) {
                LOGGER.debug("Artifact '{}' does not match deployable type '{}'.", hasUri, deployableType);
                continue;
            }

            final ResponseEntity<FileInfo> fileResponse = restTemplate.exchange(hasUri.getUri(), HttpMethod.GET, HttpEntity.EMPTY, FileInfo.class);
            if (StringUtils.equalsIgnoreCase(artifactSha1, fileResponse.getBody().getChecksums().getSha1())) {
                artifact = fileResponse.getBody();
                artifact.setDeployableType(deployableType);
                break;
            }
        }

        LOGGER.info("Build '{}' #{} deployable artifact information: {}", build, number, artifact);

        return artifact;
    }

}
