package fr.osb.deployapi.controller;

import fr.osb.deployapi.model.BuildInfo;
import fr.osb.deployapi.model.BuildsNumbers;
import fr.osb.deployapi.model.FileInfo;
import fr.osb.deployapi.service.RepositoryManagerService;
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
@RequestMapping("/rest")
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

    @RequestMapping(value = "/build-info/{build}/{number}", method = RequestMethod.GET)
    BuildInfo getBuildInfo(@PathVariable("build") final String build,
                           @PathVariable("number") final Integer number) {

        LOGGER.info("Retrieving build '{}' #{} information.", build, number);

        return repositoryManagerService.getBuildInfo(build, number);
    }

    @RequestMapping(value = "/artifact-info/{build}/{number}", method = RequestMethod.GET)
    FileInfo getArtifactInfo(@PathVariable("build") final String build,
                             @PathVariable("number") final Integer number) {

        LOGGER.info("Retrieving deployable artifact for build '{}' #{}.", build, number);

        return repositoryManagerService.getBuildArtifact(build, number);
    }

    @RequestMapping(value = "/deploy/{env}/{build}", method = RequestMethod.GET)
    void deploy(@PathVariable("env") final String env,
                @PathVariable("build") final String build) {

        LOGGER.info("Deploying build '{}' latest version on environment '{}'.", build, env);

        final BuildsNumbers buildsNumbers = repositoryManagerService.getBuildNumbers(build);
        deploy(env, build, buildsNumbers.getLatest().getNumber());
    }

    @RequestMapping(value = "/deploy/{env}/{build}/{number}", method = RequestMethod.GET)
    void deploy(@PathVariable("env") final String env,
                @PathVariable("build") final String build,
                @PathVariable("number") final Integer number) {

        LOGGER.info("Deploying build '{}' #{} on environment '{}'.", build, number, env);

        // TODO
    }

}
