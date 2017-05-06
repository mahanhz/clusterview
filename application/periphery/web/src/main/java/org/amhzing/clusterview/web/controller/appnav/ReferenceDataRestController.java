package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.core.boundary.enter.ActivityService;
import org.amhzing.clusterview.core.boundary.enter.ClusterService;
import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.web.api.ClusterDTO;
import org.amhzing.clusterview.web.api.ClustersDTO;
import org.amhzing.clusterview.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.web.api.ReferenceActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.infra.user.UserUtil.USER_COUNTRY;
import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.homeLink;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/referencedata", produces = APPLICATION_JSON_V1_VALUE)
public class ReferenceDataRestController {

    private ActivityService activityService;
    private CoreActivityService coreActivityService;
    private ClusterService clusterService;

    @Autowired
    public ReferenceDataRestController(final ActivityService activityService,
                                       final CoreActivityService coreActivityService,
                                       final ClusterService clusterService) {
        this.activityService = notNull(activityService);
        this.coreActivityService = notNull(coreActivityService);
        this.clusterService = notNull(clusterService);
    }

    @GetMapping(path = "/activities")
    public ResponseEntity<ReferenceActivitiesDTO> activities() {
        final List<Activity> activities = activityService.activities();

        final ControllerLinkBuilder selfLink = linkTo(methodOn(ReferenceDataRestController.class).activities());

        final ReferenceActivitiesDTO activitiesDto = new ReferenceActivitiesDTO(activitiesDto(activities));
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());

        return ResponseEntity.ok(activitiesDto);
    }

    @GetMapping(path = "/coreactivities")
    public ResponseEntity<ReferenceActivitiesDTO> coreActivities() {
        final List<CoreActivity> coreActivities = coreActivityService.coreActivities();

        final ControllerLinkBuilder selfLink = linkTo(methodOn(ReferenceDataRestController.class).coreActivities());

        final ReferenceActivitiesDTO coreActivitiesDto = new ReferenceActivitiesDTO(coreActivitiesDto(coreActivities));
        coreActivitiesDto.add(selfLink.withSelfRel());
        coreActivitiesDto.add(homeLink());

        return ResponseEntity.ok(coreActivitiesDto);
    }

    @GetMapping(path = "/clusters")
    public ResponseEntity<ClustersDTO> clusters(final HttpServletRequest request) {
        final String userCountry = (String) request.getAttribute(USER_COUNTRY);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(userCountry));
        final ControllerLinkBuilder selfLink = linkTo(ReferenceDataRestController.class).slash("/clusters");

        final ClustersDTO clustersDto = new ClustersDTO(clustersDto(clusters));
        clustersDto.add(selfLink.withSelfRel());
        clustersDto.add(homeLink());

        return ResponseEntity.ok(clustersDto);
    }

    private List<ClusterDTO> clustersDto(final List<Cluster.Id> clusters) {
        return clusters.stream()
                       .map(cluster -> new ClusterDTO(cluster.getId()))
                       .sorted(comparing(cluster -> cluster.name))
                       .collect(toList());
    }

    private List<ReferenceActivityDTO> coreActivitiesDto(final List<CoreActivity> coreActivities) {
        return coreActivities.stream()
                             .map(coreActivity -> coreActivityDto(coreActivity))
                             .sorted(comparing(a -> a.name))
                             .collect(Collectors.toList());
    }

    private static ReferenceActivityDTO coreActivityDto(final CoreActivity coreActivity) {
        return new ReferenceActivityDTO(coreActivity.getId().getId(), coreActivity.getName());
    }

    private List<ReferenceActivityDTO> activitiesDto(final List<Activity> activities) {
        return activities.stream()
                         .map(activity -> activityDto(activity))
                         .sorted(comparing(a -> a.name))
                         .collect(Collectors.toList());
    }

    private static ReferenceActivityDTO activityDto(final Activity activity) {
        return new ReferenceActivityDTO(activity.getId().getId(), activity.getName());
    }
}
