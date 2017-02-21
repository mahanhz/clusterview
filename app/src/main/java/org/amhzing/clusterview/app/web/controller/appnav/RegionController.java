package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.web.controller.base.AbstractController;
import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.RegionRestController;
import org.amhzing.clusterview.backend.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.backend.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import static org.amhzing.clusterview.app.web.controller.appnav.CountryController.STATISTICS_MODEL_ATTR;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class RegionController extends AbstractController {

    private RegionRestController regionRestController;

    @Autowired
    public RegionController(final RegionRestController regionRestController) {
        this.regionRestController = notNull(regionRestController);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}")
    public ModelAndView region(@ModelAttribute final RegionPath regionPath,
                               final Model model) {

        final ActivityStatisticModel statistics = regionRestController.region(regionPath);
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(regionPath.getCountry() + "/" + regionPath.getRegion());
    }
}
