package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.Region;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegionController extends AbstractController {

    @GetMapping(path = "/{country}/{region}")
    public ModelAndView region(@ModelAttribute @Valid final Region region,
                               final BindingResult bindingResult) {
        return new ModelAndView(region.getCountry() + "/" + region.getRegion());
    }
}
