package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.StatisticAdapter;
import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.ClustersDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.CoursesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.HistoricalActivitiesDTO;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/statsview", produces = APPLICATION_JSON_V1_VALUE)
public class StatisticRestController {

    public static final String PARTICIPANT = "participant";
    public static final String HISTORY = "/history";
    public static final String ACTIVITY_STATS = "activitystats";
    public static final String COURSE_STATS = "coursestats";

    private StatisticAdapter statisticAdapter;
    private ClusterAdapter clusterAdapter;

    @Autowired
    public StatisticRestController(final StatisticAdapter statisticAdapter,
                                   final ClusterAdapter clusterAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
        this.clusterAdapter = notNull(clusterAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/" + ACTIVITY_STATS)
    public ResponseEntity<Resource<ActivitiesDTO>> activityStats(@PathVariable final String country) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).activityStats(country));

        final Resource<ActivitiesDTO> activitiesDto = new Resource<>(statisticAdapter.countryActivityStatistics(country));
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());
        activitiesDto.add(countryLink(country));
        activitiesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/" + COURSE_STATS)
    public ResponseEntity<Resource<CoursesDTO>> courseStats(@PathVariable final String country) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).courseStats(country));

        final Resource<CoursesDTO> coursesDto = new Resource<>(statisticAdapter.countryCourseStatistics(country));
        coursesDto.add(selfLink.withSelfRel());
        coursesDto.add(homeLink());
        coursesDto.add(countryLink(country));
        coursesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(coursesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/" + ACTIVITY_STATS)
    public ResponseEntity<Resource<ActivitiesDTO>> regionActivityStats(@PathVariable final String country,
                                                             @PathVariable final String region) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).regionActivityStats(country, region));

        final Resource<ActivitiesDTO> activitiesDto = new Resource<>(statisticAdapter.regionActivityStatistics(region));
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());
        activitiesDto.add(countryLink(country));
        activitiesDto.add(regionLink(country, region));
        activitiesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/" + COURSE_STATS)
    public ResponseEntity<Resource<CoursesDTO>> regionCourseStats(@PathVariable final String country,
                                                        @PathVariable final String region) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).regionCourseStats(country, region));

        final Resource<CoursesDTO> coursesDto = new Resource<>(statisticAdapter.regionCourseStatistics(region));
        coursesDto.add(selfLink.withSelfRel());
        coursesDto.add(homeLink());
        coursesDto.add(countryLink(country));
        coursesDto.add(regionLink(country, region));
        coursesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(coursesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/{cluster}/" + ACTIVITY_STATS)
    public ResponseEntity<Resource<ActivitiesDTO>> clusterActivityStats(@PathVariable final String country,
                                                              @PathVariable final String region,
                                                              @PathVariable final String cluster) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).clusterActivityStats(country, region, cluster));

        final Resource<ActivitiesDTO> activitiesDto = new Resource<>(statisticAdapter.clusterActivityStatistics(cluster));
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());
        activitiesDto.add(countryLink(country));
        activitiesDto.add(regionLink(country, region));
        activitiesDto.add(clusterLink(country, region, cluster));
        activitiesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/{cluster}/" + COURSE_STATS)
    public ResponseEntity<Resource<CoursesDTO>> clusterCourseStats(@PathVariable final String country,
                                                         @PathVariable final String region,
                                                         @PathVariable final String cluster) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).clusterCourseStats(country, region, cluster));

        final Resource<CoursesDTO> coursesDto = new Resource<>(statisticAdapter.clusterCourseStatistics(cluster));
        coursesDto.add(selfLink.withSelfRel());
        coursesDto.add(homeLink());
        coursesDto.add(countryLink(country));
        coursesDto.add(regionLink(country, region));
        coursesDto.add(clusterLink(country, region, cluster));
        coursesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(coursesDto);
    }

    @LogExecutionTime
    @GetMapping(path = HISTORY + "/{country}")
    public ResponseEntity<ResourceSupport> history(@PathVariable final String country) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).history(country));

        final ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(selfLink.withSelfRel());
        resourceSupport.add(homeLink());
        resourceSupport.add(countryLink(country));
        resourceSupport.add(clusterHistoryLinks(country));

        return ResponseEntity.ok(resourceSupport);
    }

    @LogExecutionTime
    @GetMapping(path = HISTORY + "/{country}/{cluster}")
    public ResponseEntity<Resource<HistoricalActivitiesDTO>> clusterHistory(@PathVariable final String country,
                                                                  @PathVariable final String cluster) {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).clusterHistory(country, cluster));
        final ControllerLinkBuilder saveLink = linkTo(methodOn(StatisticEditRestController.class).saveHistory(country, cluster));

        final Resource<HistoricalActivitiesDTO> historicalActivitiesDto = new Resource<>(statisticAdapter.clusterHistory(cluster));
        historicalActivitiesDto.add(selfLink.withSelfRel());
        historicalActivitiesDto.add(homeLink());
        historicalActivitiesDto.add(countryLink(country));
        historicalActivitiesDto.add(statsHistoryLink(country));
        historicalActivitiesDto.add(saveLink.withRel(REL_STATS_SAVE_HISTORY));

        return ResponseEntity.ok(historicalActivitiesDto);
    }

    protected List<Link> clusterHistoryLinks(final String country) {
        final ClustersDTO clusters = clusterAdapter.clusters(country);

        return clusters.clusters.stream()
                                .map(cluster -> linkTo(linkToValue(country, cluster)).withRel(rel(cluster)))
                                .collect(Collectors.toList());
    }

    private String rel(final ClusterDTO cluster) {
        return CLUSTER_STATS_HISTORY_PREFIX + cluster.name;
    }

    private ResponseEntity<Resource<HistoricalActivitiesDTO>> linkToValue(final String country, final ClusterDTO cluster) {
        return methodOn(StatisticRestController.class).clusterHistory(country, cluster.name);
    }
}
