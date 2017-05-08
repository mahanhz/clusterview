package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.RegionAdapter;
import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.GroupsDTO;
import org.amhzing.clusterview.web.controller.base.AbstractRestController;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RegionRestController extends AbstractRestController {

    private RegionAdapter regionAdapter;

    @Autowired
    public RegionRestController(final RegionAdapter regionAdapter) {
        this.regionAdapter = notNull(regionAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}")
    public ResponseEntity<ResourceSupport> region(@PathVariable final String country,
                                                  @PathVariable final String region) {

        final ControllerLinkBuilder regionLink = linkTo(methodOn(RegionRestController.class).region(country, region));
        final ControllerLinkBuilder activityStatsLink = linkTo(methodOn(StatisticRestController.class).regionActivityStats(country, region));
        final ControllerLinkBuilder courseStatsLink = linkTo(methodOn(StatisticRestController.class).regionCourseStats(country, region));

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(regionLink.withSelfRel());
        resourceSupport.add(homeLink());
        resourceSupport.add(countryLink(country));
        resourceSupport.add(clusterLinks(country, region));
        resourceSupport.add(activityStatsLink.withRel(REL_STATS_ACTIVITY));
        resourceSupport.add(courseStatsLink.withRel(REL_STATS_COURSE));
        resourceSupport.add(statsHistoryLink(country));

        return ResponseEntity.ok(resourceSupport);
    }

    private List<Link> clusterLinks(final String country, final String region) {
        final List<ClusterDTO> clusters = regionAdapter.clusters(region);

        return clusters.stream()
                       .map(cluster -> linkTo(linkToValue(country, region, cluster.name)).withRel(rel(cluster.name)))
                       .collect(toList());
    }

    private String rel(final String cluster) {
        return CLUSTER_PREFIX + cluster;
    }

    private ResponseEntity<Resource<GroupsDTO>> linkToValue(final String country, final String region, final String cluster) {
        return methodOn(GroupRestController.class).groups(country, region, cluster);
    }
}
