package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.appui.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.appui.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.appui.web.model.ClusterNameModel;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.amhzing.clusterview.appui.web.controller.appnav.StatisticController.HISTORY;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/statsedit")
public class StatisticEditController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticEditController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @PostMapping(path = HISTORY + "/{country}")
    public String saveHistory(@ModelAttribute @PathVariable final String country,
                              @ModelAttribute @Valid final ClusterNameModel clusterNameModel,
                              final BindingResult bindingResult,
                              final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return statsView(country);
        }

        final String cluster = clusterNameModel.getName();

        final ActivityStatisticModel currentStats = statisticAdapter.clusterStats(cluster);
        statisticAdapter.saveStatsHistory(cluster, currentStats);

        redirectAttributes.addFlashAttribute("statsHistoryMessage", cluster + " history saved");

        return redirectToStatsView(country);
    }

    private String statsView(final String country) {
        return "/stats-history";
    }

    private String redirectToStatsView(final String country) {
        return "redirect:/statsview" + HISTORY + "/" + country;
    }
}
