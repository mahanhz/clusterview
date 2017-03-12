package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping(path = BASE_PATH + "/statsview", produces = APPLICATION_JSON_V1_VALUE)
public class StatisticRestController {

    public static final String HISTORY = "/history";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public StatisticRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/activityStats")
    public ResponseEntity<ActivityStatisticModel> activityStats(@PathVariable final String country) {
        return ResponseEntity.ok(statisticAdapter.countryStats(country));
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/courseStats")
    public ResponseEntity<List<CourseStatisticModel>> courseStats(@PathVariable final String country) {
        return ResponseEntity.ok(statisticAdapter.countryCourseStats(country));
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/activityStats")
    public ResponseEntity<ActivityStatisticModel> regionActivityStats(final RegionPath regionPath) {
        return ResponseEntity.ok(statisticAdapter.regionStats(regionPath.getRegion()));
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/courseStats")
    public ResponseEntity<List<CourseStatisticModel>> regionCourseStats(final RegionPath regionPath) {
        return ResponseEntity.ok(statisticAdapter.regionCourseStats(regionPath.getRegion()));
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
