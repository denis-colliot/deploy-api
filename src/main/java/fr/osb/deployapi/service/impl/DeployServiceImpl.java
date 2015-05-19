package fr.osb.deployapi.service.impl;

import fr.osb.deployapi.repository.IsArtifactInfo;
import fr.osb.deployapi.repository.RepositoryManager;
import fr.osb.deployapi.service.DeployService;
import fr.osb.deployapi.service.RemoteCommandService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

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
    private RepositoryManager repositoryManager;

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

        final List<Integer> buildNumbers = repositoryManager.getBuildNumbers(build);
        if (CollectionUtils.isEmpty(buildNumbers)) {
            throw new UnsupportedOperationException("No build number found for build '" + build + "'.");
        }

        final Integer latest = buildNumbers.get(0);

        LOGGER.debug("Latest build '{}': {}.", build, latest);

        deploy(env, build, latest);
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

        final IsArtifactInfo artifact = repositoryManager.getBuildArtifact(build, number);

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

        LOGGER.debug("Scripts Format: '{}'", scriptsFormat);
        LOGGER.debug("Host[{}]: '{}'", env, host);
        LOGGER.debug("Username[{}]: '{}'", env, username);
        LOGGER.debug("Password[{}]: '{}'", env, password);
        LOGGER.debug("Scripts Folder[{}]: '{}'", env, scriptsFolder);

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
