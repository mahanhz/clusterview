package org.amhzing.clusterview.web.controller.se;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegionController {

    @GetMapping(path = "/{country}/{region}")
    public ModelAndView index(@PathVariable final String country,
                              @PathVariable final String region) {
        return new ModelAndView(country + "/" + region);
    }

}
