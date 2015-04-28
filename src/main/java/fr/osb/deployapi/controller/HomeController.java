package fr.osb.deployapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 28/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    String home() {
        return "index";
    }

}
