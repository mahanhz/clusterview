package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class MainController extends AbstractController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public MainController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @GetMapping(path = "/{country}")
    public ModelAndView country(@ModelAttribute @PathVariable final String country) {
        statisticAdapter.countryStats(country);
        return new ModelAndView(country + "/index");
    }
}
