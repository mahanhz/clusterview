package org.amhzing.clusterview.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController extends AbstractController {

    @GetMapping(path = "/{country}")
    public ModelAndView country(@ModelAttribute @PathVariable final String country) {
        return new ModelAndView(country + "/index");
    }
}
