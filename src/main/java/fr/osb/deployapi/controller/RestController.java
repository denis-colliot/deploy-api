package fr.osb.deployapi.controller;

import fr.osb.deployapi.model.BuildInfo;
import fr.osb.deployapi.model.BuildsNumbers;
import fr.osb.deployapi.model.FileInfo;
import fr.osb.deployapi.service.DeployService;
import fr.osb.deployapi.service.RepositoryManagerService;
import fr.osb.deployapi.util.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(Paths.REST)
public class RestController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);

    /**
     * Injected service.
     */
    @Autowired
    private RepositoryManagerService repositoryManagerService;

    /**
     * Injected service.
     */
    @Autowired
    private DeployService deployService;

    @RequestMapping(value = Paths.REST_BUILD_INFO + "/{build}/{number}", method = RequestMethod.GET)
    BuildInfo getBuildInfo(@PathVariable("build") final String build,
                           @PathVariable("number") final Integer number) {

        LOGGER.info("Retrieving build '{}' #{} information.", build, number);

        return repositoryManagerService.getBuildInfo(build, number);
    }

    @RequestMapping(value = Paths.REST_ARTIFACT_INFO + "/{build}/{number}", method = RequestMethod.GET)
    FileInfo getArtifactInfo(@PathVariable("build") final String build,
                             @PathVariable("number") final Integer number) {

        LOGGER.info("Retrieving deployable artifact for build '{}' #{}.", build, number);

        return repositoryManagerService.getBuildArtifact(build, number);
    }

    @RequestMapping(value = Paths.REST_DEPLOY + "/{env}/{build}", method = RequestMethod.PUT)
    void deploy(@PathVariable("env") final String env,
                @PathVariable("build") final String build) {

        LOGGER.info("Deploying build '{}' latest version on environment '{}'.", build, env);

        final BuildsNumbers buildsNumbers = repositoryManagerService.getBuildNumbers(build);
        deploy(env, build, buildsNumbers.getLatest().getNumber());
    }

    @RequestMapping(value = Paths.REST_DEPLOY + "/{env}/{build}/{number}", method = RequestMethod.PUT)
    void deploy(@PathVariable("env") final String env,
                @PathVariable("build") final String build,
                @PathVariable("number") final Integer number) {

        LOGGER.info("Deploying build '{}' #{} on environment '{}'.", build, number, env);

        deployService.deploy(env, build, number);
    }

}
