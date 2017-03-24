package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
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
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CountryRestController extends AbstractRestController {

    // FIXME - Hardcoded - Need to get regions from a service
    private static final Map<String, List<String>> COUNTRY_REGIONS = ImmutableMap.of("se", ImmutableList.of("central", "northern", "southern"));

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ResponseEntity<ResourceSupport> country(@PathVariable final String country) {

        final ControllerLinkBuilder countryLink = linkTo(methodOn(CountryRestController.class).country(country));
        final ControllerLinkBuilder activityStatsLink = linkTo(methodOn(StatisticRestController.class).activityStats(country));
        final ControllerLinkBuilder courseStatsLink = linkTo(methodOn(StatisticRestController.class).courseStats(country));

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(countryLink.withSelfRel());
        resourceSupport.add(homeLink());
        resourceSupport.add(regionLinks(country));
        resourceSupport.add(activityStatsLink.withRel(REL_STATS_ACTIVITY));
        resourceSupport.add(courseStatsLink.withRel(REL_STATS_COURSE));
        resourceSupport.add(statsHistoryLink(country));

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