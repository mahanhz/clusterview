package org.amhzing.clusterview.app.web.controller.rest;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.DatedActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.HistoricalStatsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.amhzing.clusterview.app.web.MediaType.APPLICATION_JSON_V1;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping(path = "/backend/statsview", produces = APPLICATION_JSON_V1)
public class StatisticRestController {

    public static final String HISTORY = "/history";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = HISTORY + "/{country}/{cluster}")
    public HistoricalStatsModel clusterHistory(@PathVariable final String country,
                                               @PathVariable final String cluster) {

        final ActivityStatisticModel currentStats = statisticAdapter.clusterStats(cluster);
        final List<DatedActivityStatisticModel> statsHistory = statisticAdapter.statsHistory(cluster);

        return HistoricalStatsModel.create(currentStats, statsHistory);
    }
}
