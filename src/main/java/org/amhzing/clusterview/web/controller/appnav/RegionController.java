package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.controller.base.AbstractController;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import static org.amhzing.clusterview.web.controller.appnav.CountryController.STATISTICS_MODEL_ATTR;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class RegionController extends AbstractController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public RegionController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @GetMapping(path = "/{country}/{region}")
    public ModelAndView region(@ModelAttribute final RegionPath regionPath,
                               final Model model) {

        final ActivityStatisticModel statistics = statisticAdapter.regionStats(regionPath.getRegion());
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(regionPath.getCountry() + "/" + regionPath.getRegion());
    }
}
