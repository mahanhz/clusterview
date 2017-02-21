package org.amhzing.clusterview.backend.web.controller.appnav;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.backend.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.backend.web.model.ClusterNameModel;
import org.amhzing.clusterview.backend.web.model.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.amhzing.clusterview.backend.web.MediaType.APPLICATION_JSON_V1;
import static org.amhzing.clusterview.backend.web.controller.appnav.StatisticRestController.*;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/backend/statsedit")
public class StatisticEditRestController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticEditRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @PostMapping(path = HISTORY + "/{country}", consumes = APPLICATION_JSON_V1)
    public SimpleResponse saveHistory(@PathVariable final String country,
                                      @RequestBody @Valid final ClusterNameModel clusterNameModel) {

        final String cluster = clusterNameModel.getName();

        final ActivityStatisticModel currentStats = statisticAdapter.clusterStats(cluster);
        statisticAdapter.saveStatsHistory(cluster, currentStats);

        return SimpleResponse.create(country, "SAVED");
    }
}
