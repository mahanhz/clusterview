package org.amhzing.clusterview.backend.web.controller.rest;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.backend.web.model.ActivityStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class CountryRestController extends AbstractRestController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public CountryRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ActivityStatisticModel country(@PathVariable final String country) {
        return statisticAdapter.countryStats(country);
    }
}