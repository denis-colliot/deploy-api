package fr.osb.deployapi.repository.artifactory;

import fr.osb.deployapi.repository.IsArtifactInfo;
import fr.osb.deployapi.repository.IsBuildInfo;
import fr.osb.deployapi.repository.RepositoryManager;
import fr.osb.deployapi.repository.artifactory.mapping.*;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * Repository manager implementation for Artifactory resource.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Service
public class ArtifactoryManager implements RepositoryManager {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactoryManager.class);

    /**
     * REST template.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Artifactory REST API URL.
     */
    @Value("${artifactory.api}")
    private String artifactoryApi;

    /**
     * Artifactory authentication.
     */
    @Value("${artifactory.auth}")
    private String artifactoryAuth;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllBuilds() {

        LOGGER.debug("Retrieving all builds identifiers.");

        final Builds builds = restTemplate.exchange(Paths.p(artifactoryApi, "build"),
                HttpMethod.GET, HttpEntity.EMPTY, Builds.class).getBody();

        LOGGER.debug("All builds: {}", builds);

        final List<String> result = new ArrayList<>();

        for (final ModelWithUri build : builds.getBuilds()) {
            result.add(build.getUri());
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getBuildNumbers(final String build) {

        if (StringUtils.isBlank(build)) {
            throw new IllegalArgumentException("Invalid build identifier.");
        }

        LOGGER.debug("Retrieving build '{}' latest version available numbers.", build);

        final BuildsNumbers buildsNumbers = restTemplate.exchange(Paths.p(artifactoryApi, "build", build),
                HttpMethod.GET, HttpEntity.EMPTY, BuildsNumbers.class).getBody();

        LOGGER.debug("Build '{}' available numbers: {}", build, buildsNumbers);

        final List<Integer> result = new ArrayList<>();

        for (final BuildsNumbers.BuildNumber number : buildsNumbers.getBuildsNumbers()) {
            result.add(number.getNumber());
        }

        Collections.sort(result);
        Collections.reverse(result);

        LOGGER.debug("Build '{}' available numbers (sorted): {}", build, result);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IsBuildInfo getBuildInfo(final String build, final Integer number) {

        if (StringUtils.isBlank(build) || number == null) {
            throw new IllegalArgumentException("Invalid build identifier.");
        }

        LOGGER.debug("Retrieving build '{}' #{} information.", build, number);

        final HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(artifactoryAuth.getBytes()));

        final BuildInfo buildInfo = restTemplate.exchange(Paths.p(artifactoryApi, "build", build, number),
                HttpMethod.GET, new HttpEntity<Object>(header), BuildInfo.class).getBody();

        LOGGER.debug("Build '{}' #{} information: {}", build, number, buildInfo);

        return buildInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IsArtifactInfo getBuildArtifact(final String build, final Integer number) {

        LOGGER.debug("Retrieving build '{}' #{} deployable artifact information.", build, number);

        // --
        // Retrieving build info.
        // --

        final BuildInfo buildInfo = (BuildInfo) getBuildInfo(build, number);

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

        final GavSearchResults searchResult = restTemplate.exchange(Paths.p(artifactoryApi, "search", "gavc?g={g}&a={a}&v={v}"),
                HttpMethod.GET, HttpEntity.EMPTY, GavSearchResults.class, module.getGav().asUrlVariables()).getBody();

        LOGGER.debug("Build '{}' #{} GAV search results: {}", build, number, searchResult);

        FileInfo artifact = null;

        for (final ModelWithUri hasUri : searchResult.getResults()) {

            if (!DeployableType.isType(hasUri.getUri(), deployableType)) {
                LOGGER.debug("Artifact '{}' does not match deployable type '{}'.", hasUri, deployableType);
                continue;
            }

            final FileInfo fileInfo = restTemplate.exchange(hasUri.getUri(), HttpMethod.GET, HttpEntity.EMPTY, FileInfo.class).getBody();
            if (StringUtils.equalsIgnoreCase(artifactSha1, fileInfo.getChecksums().getSha1())) {
                artifact = fileInfo;
                artifact.setDeployableType(deployableType);
                break;
            }
        }

        LOGGER.debug("Build '{}' #{} deployable artifact information: {}", build, number, artifact);

        return artifact;
    }

}
