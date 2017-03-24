package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.app.api.statistic.CoursesDTO;
import org.amhzing.clusterview.app.api.statistic.HistoricalActivitiesDTO;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.web.model.RegionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.amhzing.clusterview.app.web.controller.rest.util.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/statsview", produces = APPLICATION_JSON_V1_VALUE)
public class StatisticRestController {

    public static final String PARTICIPANT = "participant";
    public static final String HISTORY = "/history";
    public static final String ACTIVITY_STATS = "activityStats";
    public static final String COURSE_STATS = "courseStats";

    private StatisticService<ActivityStatistic> activityStatisticService;
    private StatisticService<CourseStatistic> courseStatisticService;
    private StatisticHistoryService statisticHistoryService;
    private ClusterService clusterService;

    @Autowired
    public StatisticRestController(final StatisticService<ActivityStatistic> activityStatisticService,
                                   final StatisticService<CourseStatistic> courseStatisticService,
                                   final StatisticHistoryService statisticHistoryService,
                                   final ClusterService clusterService) {
        this.activityStatisticService = notNull(activityStatisticService);
        this.courseStatisticService = notNull(courseStatisticService);
        this.statisticHistoryService = notNull(statisticHistoryService);
        this.clusterService = notNull(clusterService);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/" + ACTIVITY_STATS)
    public ResponseEntity<ActivitiesDTO> activityStats(@PathVariable final String country) {
        final ActivityStatistic statistics = activityStatisticService.statistics(Country.Id.create(country));

        final ActivitiesDTO activitiesDto = activitiesDto(statistics);

        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).activityStats(country));
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());
        activitiesDto.add(countryLink(country));
        activitiesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/" + COURSE_STATS)
    public ResponseEntity<CoursesDTO> courseStats(@PathVariable final String country) {
        final CourseStatistic statistics = courseStatisticService.statistics(Country.Id.create(country));

        final CoursesDTO coursesDto = coursesDto(statistics);

        final ControllerLinkBuilder selfLink = linkTo(methodOn(StatisticRestController.class).courseStats(country));
        coursesDto.add(selfLink.withSelfRel());
        coursesDto.add(homeLink());
        coursesDto.add(countryLink(country));
        coursesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(coursesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/" + ACTIVITY_STATS)
    public ResponseEntity<ActivitiesDTO> regionActivityStats(final RegionPath regionPath) {
        final ActivityStatistic statistics = activityStatisticService.statistics(Region.Id.create(regionPath.getRegion()));

        final ActivitiesDTO activitiesDto = activitiesDto(statistics);

        final String country = regionPath.getCountry();
        final String region = regionPath.getRegion();

        final ControllerLinkBuilder selfLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(ACTIVITY_STATS);

        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());
        activitiesDto.add(countryLink(country));
        activitiesDto.add(regionLink(country, region));
        activitiesDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}/" + COURSE_STATS)
    public ResponseEntity<CoursesDTO> regionCourseStats(final RegionPath regionPath) {
        final CourseStatistic statistics = courseStatisticService.statistics(Region.Id.create(regionPath.getRegion()));

        final CoursesDTO coursesDto = coursesDto(statistics);

        final String country = regionPath.getCountry();
        final String region = regionPath.getRegion();

        final ControllerLinkBuilder selfLink = linkTo(StatisticRestController.class).slash(country).slash(region).slash(COURSE_STATS);

        coursesDto.add(selfLink.withSelfRel());
        coursesDto.add(homeLink());
        coursesDto.add(countryLink(country));
        coursesDto.add(regionLink(country, region));
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
    public ResponseEntity<HistoricalActivitiesDTO> clusterHistory(@PathVariable final String country,
                                                                  @PathVariable final String cluster) {

        final ActivityStatistic currentStats = activityStatisticService.statistics(Cluster.Id.create(cluster));
        final List<DatedActivityStatistic> datedStats = statisticHistoryService.history(Cluster.Id.create(cluster));

        final HistoricalActivitiesDTO historicalActivitiesDto = historicalActivitiesDto(currentStats, datedStats);

        final ControllerLinkBuilder selfLink = linkTo(StatisticRestController.class).slash(HISTORY).slash(country).slash(cluster);
        final ControllerLinkBuilder saveLink = linkTo(StatisticEditRestController.class).slash(HISTORY).slash(country).slash(cluster);

        historicalActivitiesDto.add(selfLink.withSelfRel());
        historicalActivitiesDto.add(homeLink());
        historicalActivitiesDto.add(countryLink(country));
        historicalActivitiesDto.add(statsHistoryLink(country));
        historicalActivitiesDto.add(saveLink.withRel(REL_STATS_SAVE_HISTORY));

        return ResponseEntity.ok(historicalActivitiesDto);
    }

    private List<Link> clusterHistoryLinks(final String country) {
        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(country));

        final List<Link> clusterLinks = clusters.stream()
                                              .map(cluster -> linkTo(StatisticRestController.class).slash(HISTORY)
                                                                                                   .slash(country)
                                                                                                   .slash(cluster.getId())
                                                                                                   .withRel(CLLUSTER_STATS_HISTORY_PREFIX + cluster.getId()))
                                              .collect(Collectors.toList());
        return clusterLinks;
    }
}
