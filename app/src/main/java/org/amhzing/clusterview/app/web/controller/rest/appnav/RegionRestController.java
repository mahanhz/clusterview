package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
import org.amhzing.clusterview.app.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.ACTIVITY_STATS;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.COURSE_STATS;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

        final ControllerLinkBuilder regionLink = linkTo(RegionRestController.class).slash(country).slash(region);
        final ControllerLinkBuilder activityStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(ACTIVITY_STATS);
        final ControllerLinkBuilder courseStatsLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(COURSE_STATS);

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(regionLink.withSelfRel());
        resourceSupport.add(homeLink());
        resourceSupport.add(countryLink(country));
        resourceSupport.add(activityStatsLink.withRel(REL_STATS_ACTIVITY));
        resourceSupport.add(courseStatsLink.withRel(REL_STATS_COURSE));
        resourceSupport.add(statsHistoryLink(country));

        return ResponseEntity.ok(resourceSupport);
    }
}
