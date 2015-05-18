package fr.osb.deployapi.service.impl;

import fr.osb.deployapi.model.BuildsNumbers;
import fr.osb.deployapi.model.FileInfo;
import fr.osb.deployapi.service.DeployService;
import fr.osb.deployapi.service.RemoteCommandService;
import fr.osb.deployapi.service.RepositoryManagerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

/**
 * {@link DeployService} implementation using SSH protocol to execute deploy script.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Service
public class DeployServiceImpl implements DeployService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployService.class);

    /**
     * Environment properties.
     */
    @Autowired
    private Environment environment;

    /**
     * Injected service.
     */
    @Autowired
    private RepositoryManagerService repositoryManagerService;

    /**
     * Injected service.
     */
    @Autowired
    private RemoteCommandService remoteCommandService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void deploy(final String env, final String build) {

        LOGGER.info("Deploying build '{}' latest number on environment '{}'.", build, env);

        final BuildsNumbers.BuildNumber latest = repositoryManagerService.getBuildNumbers(build).getLatest();

        LOGGER.debug("Latest build '{}': {}.", build, latest);

        deploy(env, build, latest.getNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deploy(final String env, final String build, final Integer number) {

        LOGGER.info("Deploying build '{}' #{} on environment '{}'.", build, number, env);

        // --
        // Retrieving deployable artifact.
        // --

        final FileInfo artifact = repositoryManagerService.getBuildArtifact(build, number);

        if (artifact == null) {
            throw new UnsupportedOperationException("No deployable artifact found for build '" + build + "' #" + number);
        }

        LOGGER.info("Artifact to deploy: {}", artifact);

        // --
        // Retrieving environment properties.
        // --

        final String host = environment.getProperty("env." + env + ".host");
        final String username = environment.getProperty("env." + env + ".username");
        final String password = environment.getProperty("env." + env + ".password");
        final String scriptsFolder = environment.getProperty("env." + env + ".scriptsFolder");
        final String scriptsFormat = environment.getProperty("env.scriptsFormat");

        if (StringUtils.isAnyBlank(host, username, password, scriptsFolder, scriptsFormat)) {
            throw new UnsupportedOperationException("Environment '" + env + "' properties are missing or invalid.");
        }

        final String script = Paths.get(scriptsFolder, scriptsFormat.replace("{build}", build)).toString();

        LOGGER.info("Target environment - host: '{}' ; username: '{}' ; script: '{}'.", host, username, script);

        // --
        // Executing remote deployment script.
        // --

        remoteCommandService.executeScript(host, username, password, script, number, artifact.getDownloadUri());
    }

}
