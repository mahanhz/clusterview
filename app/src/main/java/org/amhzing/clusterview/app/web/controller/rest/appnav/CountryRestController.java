package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.application.RegionService;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
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

import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CountryRestController extends AbstractRestController {

    private RegionService regionService;

    @Autowired
    public CountryRestController(final RegionService regionService) {
        this.regionService = notNull(regionService);
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
        final List<Region.Id> regions = regionService.regions(Country.Id.create(country));

        final List<Link> regionLinks = regions.stream()
                                              .map(region -> linkTo(RegionRestController.class).slash(country)
                                                                                               .slash(region.getId())
                                                                                               .withRel(REGION_PREFIX + region.getId()))
                                              .collect(Collectors.toList());

        return regionLinks;
    }
}