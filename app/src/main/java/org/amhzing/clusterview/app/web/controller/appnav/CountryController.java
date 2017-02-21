package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.backend.web.adapter.StatisticAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class CountryController {

    public static final String STATISTICS_MODEL_ATTR = "statistics";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public CountryController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ModelAndView country(@ModelAttribute @PathVariable final String country,
                                final Model model) {

        final ActivityStatisticModel statistics = statisticAdapter.countryStats(country);
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(country + "/index");
    }
}
