package org.amhzing.clusterview.app.web.controller.rest;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class RegionRestController extends AbstractRestController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public RegionRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}")
    public ActivityStatisticModel region(@ModelAttribute final RegionPath regionPath) {

        return statisticAdapter.regionStats(regionPath.getRegion());
    }
}
