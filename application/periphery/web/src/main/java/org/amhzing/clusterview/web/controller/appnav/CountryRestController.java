package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.RegionAdapter;
import org.amhzing.clusterview.adapter.web.api.RegionDTO;
import org.amhzing.clusterview.web.controller.base.AbstractRestController;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CountryRestController extends AbstractRestController {

    private RegionAdapter regionAdapter;

    @Autowired
    public CountryRestController(final RegionAdapter regionAdapter) {
        this.regionAdapter = notNull(regionAdapter);
    }

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
        final List<RegionDTO> regions = regionAdapter.regions(country);

        return regions.stream()
                      .map(region -> linkTo(linkToValue(country, region.name)).withRel(rel(region.name)))
                      .collect(Collectors.toList());
    }

    private String rel(final String region) {
        return REGION_PREFIX + region;
    }

    private ResponseEntity<ResourceSupport> linkToValue(final String country, final String region) {
        return methodOn(RegionRestController.class).region(country, region);
    }
}