package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.web.controller.base.AbstractController;
import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.CountryRestController;
import org.amhzing.clusterview.backend.web.model.ActivityStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class CountryController extends AbstractController {

    public static final String STATISTICS_MODEL_ATTR = "statistics";

    private CountryRestController countryRestController;

    @Autowired
    public CountryController(final CountryRestController countryRestController) {
        this.countryRestController = notNull(countryRestController);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ModelAndView country(@ModelAttribute @PathVariable final String country,
                                final Model model) {

        final ActivityStatisticModel statistics = countryRestController.country(country);
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(country + "/index");
    }
}
