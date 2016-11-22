package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.RegionPath;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegionController extends AbstractController {

    @GetMapping(path = "/{country}/{region}")
    public ModelAndView region(@ModelAttribute final RegionPath regionPath) {
        return new ModelAndView(regionPath.getCountry() + "/" + regionPath.getRegion());
    }
}
