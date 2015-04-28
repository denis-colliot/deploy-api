package fr.osb.deployapi.controller;

import fr.osb.deployapi.model.Builds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Controller
@RequestMapping("/builds")
public class BuildsController {

    @Value("${repositoryManager.api}")
    private String repositoryManagerApi;

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView getAllBuilds() {

        final ModelAndView model = new ModelAndView("builds");
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<Builds> response = restTemplate.exchange(repositoryManagerApi + "/build/",
                HttpMethod.GET, HttpEntity.EMPTY, Builds.class);

        model.addObject("builds", response.getBody());

        return model;
    }

    @RequestMapping(value = "/{build}", method = RequestMethod.GET)
    ModelAndView getBuildVersions(final String build) {

        final ModelAndView model = new ModelAndView("builds");
        final RestTemplate restTemplate = new RestTemplate();

        final ResponseEntity<Builds> response = restTemplate.exchange(repositoryManagerApi + "/build/" + build,
                HttpMethod.GET, HttpEntity.EMPTY, Builds.class);

        model.addObject("builds", response.getBody());

        return model;
    }

}
