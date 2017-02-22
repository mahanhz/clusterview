package org.amhzing.clusterview.app.web.controller.rest;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.app.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityModel;
import org.amhzing.clusterview.app.web.model.ClusterNameModel;
import org.amhzing.clusterview.app.web.model.CoreActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.amhzing.clusterview.app.web.MediaType.APPLICATION_JSON_V1;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping(path = "/rest/referencedata", produces = APPLICATION_JSON_V1)
public class ReferenceDataRestController {

    private StatisticAdapter statisticAdapter;
    private ActivityAdapter activityAdapter;
    private CoreActivityAdapter coreActivityAdapter;

    @Autowired
    public ReferenceDataRestController(final StatisticAdapter statisticAdapter,
                                       final ActivityAdapter activityAdapter,
                                       final CoreActivityAdapter coreActivityAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
        this.activityAdapter = notNull(activityAdapter);
        this.coreActivityAdapter = notNull(coreActivityAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/activities")
    public List<ActivityModel> activities() {
        return activityAdapter.activities();
    }

    @LogExecutionTime
    @GetMapping(path = "/coreactivities")
    public List<CoreActivityModel> coreActivities() {
        return coreActivityAdapter.coreActivities();
    }

    @LogExecutionTime
    @GetMapping(path = "/clusters/{country}")
    public List<ClusterNameModel> clusters(@PathVariable final String country) {
        return statisticAdapter.clusters(country);
    }
}
