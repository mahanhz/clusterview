package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
import org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController;
import org.amhzing.clusterview.app.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RegionRestController extends AbstractRestController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public RegionRestController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}")
    public ResponseEntity<ResourceSupport> region(final RegionPath regionPath) {

        final String country = regionPath.getCountry();
        final String region = regionPath.getRegion();

        final ControllerLinkBuilder homeLink = linkTo(IndexRestController.class);
        final ControllerLinkBuilder countryLink = linkTo(methodOn(CountryRestController.class).country(country));
        final ControllerLinkBuilder regionLink = linkTo(RegionRestController.class).slash(region);
        final ControllerLinkBuilder activityStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash("activityStats");
        final ControllerLinkBuilder courseStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash("courseStats");

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(regionLink.withSelfRel());
        resourceSupport.add(homeLink.withRel("home"));
        resourceSupport.add(countryLink.withRel("country-" + country));
        resourceSupport.add(activityStatsLink.withRel("stats-activity"));
        resourceSupport.add(courseStatsLink.withRel("stats-course"));

        return ResponseEntity.ok(resourceSupport);
    }
}
