package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.DatedActivityStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.amhzing.clusterview.web.controller.MainController.STATISTICS_MODEL_ATTR;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/statsview")
public class StatisticController {

    public static final String STATS_HISTORY_MODEL_ATTR = "statsHistory";
    public static final String HISTORY = "/history";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @GetMapping(path = HISTORY + "/{country}/{cluster}")
    public String clusterHistory(@ModelAttribute @PathVariable final String country,
                                 @ModelAttribute @PathVariable final String cluster,
                                 final Model model) {

        final ActivityStatisticModel currentStats = statisticAdapter.clusterStats(cluster);
        model.addAttribute(STATISTICS_MODEL_ATTR, currentStats);

        final List<DatedActivityStatisticModel> statsHistory = statisticAdapter.statsHistory(cluster);
        model.addAttribute(STATS_HISTORY_MODEL_ATTR, statsHistory);

        return country + "/stats-history";
    }
}
