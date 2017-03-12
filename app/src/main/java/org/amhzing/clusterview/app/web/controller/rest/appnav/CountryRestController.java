package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
import org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CountryRestController extends AbstractRestController {

    // FIXME - Hardcoded - Need to get regions from an adapter
    private static final Map<String, List<String>> COUNTRY_REGIONS = ImmutableMap.of("se", ImmutableList.of("central", "northern", "southern"));
    private static final String REGION_PREFIX = "region-";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public CountryRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ResponseEntity<ResourceSupport> country(@PathVariable final String country) {

        final ControllerLinkBuilder homeLink = linkTo(IndexRestController.class);
        final ControllerLinkBuilder countryLink = linkTo(methodOn(CountryRestController.class).country(country));
        final ControllerLinkBuilder activityStatsLink = linkTo(methodOn(StatisticRestController.class).activityStats(country));
        final ControllerLinkBuilder courseStatsLink = linkTo(methodOn(StatisticRestController.class).courseStats(country));

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(countryLink.withSelfRel());
        resourceSupport.add(homeLink.withRel("home"));
        resourceSupport.add(regionLinks(country));
        resourceSupport.add(activityStatsLink.withRel("stats-activity"));
        resourceSupport.add(courseStatsLink.withRel("stats-course"));

        return ResponseEntity.ok(resourceSupport);
    }

    private List<Link> regionLinks(final String country) {
        final List<String> regions = COUNTRY_REGIONS.getOrDefault(country, emptyList());

        final List<Link> regionLinks = regions.stream()
                                              .map(region -> linkTo(RegionRestController.class).slash(country).slash(region).withRel(REGION_PREFIX + region))
                                              .collect(Collectors.toList());

        return regionLinks;
    }
}