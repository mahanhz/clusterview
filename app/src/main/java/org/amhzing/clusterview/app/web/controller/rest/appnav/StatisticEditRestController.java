package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.ClusterNameModel;
import org.amhzing.clusterview.app.web.model.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = BASE_PATH + "/statsedit")
public class StatisticEditRestController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticEditRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @PostMapping(path = StatisticRestController.HISTORY + "/{country}", consumes = APPLICATION_JSON_V1_VALUE)
    public SimpleResponse saveHistory(@PathVariable final String country,
                                      @RequestBody @Valid final ClusterNameModel clusterNameModel) {

        final String cluster = clusterNameModel.getName();

        final ActivityStatisticModel currentStats = statisticAdapter.clusterStats(cluster);
        statisticAdapter.saveStatsHistory(cluster, currentStats);

        return SimpleResponse.create(country, "SAVED");
    }
}
