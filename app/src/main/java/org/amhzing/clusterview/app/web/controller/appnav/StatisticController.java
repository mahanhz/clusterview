package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.StatisticRestController;
import org.amhzing.clusterview.backend.web.model.ClusterNameModel;
import org.amhzing.clusterview.backend.web.model.HistoricalStatsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/statsview")
public class StatisticController {

    public static final String CLUSTERS_MODEL_ATTR = "clusters";
    public static final String STATS_HISTORY_MODEL_ATTR = "statsHistory";
    public static final String HISTORY = "/history";

    private StatisticRestController statisticRestController;

    @Autowired
    public StatisticController(final StatisticRestController statisticRestController) {
        this.statisticRestController = notNull(statisticRestController);
    }

    @ModelAttribute
    public ClusterNameModel clusterNameModel() {
        return new ClusterNameModel();
    }

    @LogExecutionTime
    @GetMapping(path = HISTORY + "/{country}")
    public String clusters(@ModelAttribute @PathVariable final String country,
                           final Model model) {

        return "/stats-history";
    }

    @LogExecutionTime
    @GetMapping(path = HISTORY + "/{country}/{cluster}")
    public String clusterHistory(@ModelAttribute @PathVariable final String country,
                                 @ModelAttribute @PathVariable final String cluster,
                                 final Model model) {

        final HistoricalStatsModel historicalStats = statisticRestController.clusterHistory(country, cluster);

        model.addAttribute(CountryController.STATISTICS_MODEL_ATTR, historicalStats.getCurrentStats());
        model.addAttribute(STATS_HISTORY_MODEL_ATTR, historicalStats.getStatsHistory());

        return "/stats-history-cluster";
    }
}
