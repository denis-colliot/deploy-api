package fr.osb.deployapi.service.impl;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployServiceImpl.class);

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
    public void deploy(final String env, final String build, final Integer number) {

        LOGGER.debug("Deploying build '{}' #{} on environment '{}'.", build, number, env);

        // --
        // Retrieving deployable artifact.
        // --

        final FileInfo artifact = repositoryManagerService.getBuildArtifact(build, number);

        if (artifact == null) {
            throw new UnsupportedOperationException("No deployable artifact found for build '" + build + "' #" + number);
        }

        LOGGER.debug("Artifact to deploy: {}", artifact);

        // --
        // Retrieving environment properties.
        // --

        final String host = environment.getProperty("env." + env + ".host");
        final String username = environment.getProperty("env." + env + ".username");
        final String password = environment.getProperty("env." + env + ".password");
        final String script = environment.getProperty("env." + env + ".script." + artifact.getDeployableType().getContainer());

        if (StringUtils.isAnyBlank(host, username, password, script)) {
            throw new UnsupportedOperationException("Environment '" + env + "' properties are missing or invalid.");
        }

        LOGGER.debug("Target environment - host: '{}' ; username: '{}' ; script: '{}'.", host, username, script);

        // --
        // Executing remote deployment script.
        // --

        remoteCommandService.executeScript(host, username, password, script, artifact.getDownloadUri());
    }

}
