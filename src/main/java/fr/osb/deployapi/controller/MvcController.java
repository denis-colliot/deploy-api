package fr.osb.deployapi.controller;

import fr.osb.deployapi.model.BuildsNumbers;
import fr.osb.deployapi.service.RepositoryManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Controller
public class MvcController {

    @Value("${project.version}")
    private String projectVersion;

    @Autowired
    private RepositoryManagerService repositoryManagerService;

    @RequestMapping("/")
    ModelAndView home() {
        return new ModelAndView("index", "projectVersion", projectVersion);
    }

    @RequestMapping(value = "/builds", method = RequestMethod.GET)
    ModelAndView getAllBuilds() {
        return new ModelAndView("builds", "builds", repositoryManagerService.getAllBuilds());
    }

    @RequestMapping(value = "/builds/{build}", method = RequestMethod.GET)
    @ResponseBody
    List<BuildsNumbers.BuildNumber> getBuildNumbers(@PathVariable("build") final String build) {
        return repositoryManagerService.getBuildNumbers(build).getBuildsNumbers();
    }

}
