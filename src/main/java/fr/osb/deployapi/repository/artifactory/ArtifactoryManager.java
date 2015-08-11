package fr.osb.deployapi.repository.artifactory;

import fr.osb.deployapi.repository.AbstractRepositoryManager;
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
import org.springframework.stereotype.Service;

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
public class ArtifactoryManager extends AbstractRepositoryManager {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactoryManager.class);

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

        final Builds builds = get(Paths.p(artifactoryApi, "build"), Builds.class);

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

        final BuildsNumbers buildsNumbers = get(Paths.p(artifactoryApi, "build", build), BuildsNumbers.class);

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
    public BuildInfo getBuildInfo(final String build, final Integer number) {

        if (StringUtils.isBlank(build) || number == null) {
            throw new IllegalArgumentException("Invalid build identifier.");
        }

        LOGGER.debug("Retrieving build '{}' #{} information.", build, number);

        final BuildInfo buildInfo = get(Paths.p(artifactoryApi, "build", build, number), BuildInfo.class, getAuth());

        LOGGER.debug("Build '{}' #{} information: {}", build, number, buildInfo);

        return buildInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FileInfo getBuildArtifact(final String build, final Integer number) {

        LOGGER.debug("Retrieving build '{}' #{} deployable artifact information.", build, number);

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
        // Retrieving build artifacts.
        // --

        final SearchResults searchResult = get(
                Paths.p(artifactoryApi, "search", "prop?build.name={buildName}&build.number={buildNumber}"),
                SearchResults.class,
                build, number);

        LOGGER.debug("Build '{}' #{} search returned {} results.", build, number, searchResult.getResults().size());
        LOGGER.debug("Build '{}' #{} search results: {}", build, number, searchResult);

        FileInfo artifact = null;

        for (final ModelWithUri hasUri : searchResult.getResults()) {

            if (!DeployableType.isType(hasUri.getUri(), deployableType)) {
                LOGGER.debug("Artifact '{}' does not match deployable type '{}'.", hasUri, deployableType);
                continue;
            }

            final FileInfo fileInfo = get(hasUri.getUri(), FileInfo.class);
            if (StringUtils.equalsIgnoreCase(artifactSha1, fileInfo.getChecksums().getSha1())) {
                artifact = fileInfo;
                artifact.setDeployableType(deployableType);
                break;
            }
        }

        LOGGER.debug("Build '{}' #{} deployable artifact information: {}", build, number, artifact);

        return artifact;
    }

    /**
     * Builds a {@code HttpEntity} instance with authorization header.
     *
     * @return The {@code HttpEntity} instance.
     */
    private HttpEntity<Object> getAuth() {
        final HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(artifactoryAuth.getBytes()));
        return new HttpEntity<>(header);
    }

}
