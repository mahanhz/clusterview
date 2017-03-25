package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.api.ClusterDTO;
import org.amhzing.clusterview.app.api.ClustersDTO;
import org.amhzing.clusterview.app.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.app.api.ReferenceActivityDTO;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.CoreActivityService;
import org.amhzing.clusterview.app.domain.model.Activity;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.homeLink;
import static org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController.USER_COUNTRY;
import static org.apache.commons.lang3.Validate.notNull;

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

    @LogExecutionTime
    @GetMapping(path = "/activities")
    public ResponseEntity<ReferenceActivitiesDTO> activities() {
        final List<Activity> activities = activityService.activities();

        final ReferenceActivitiesDTO activitiesDto = new ReferenceActivitiesDTO(activitiesDto(activities));
        activitiesDto.add(homeLink());

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/coreactivities")
    public ResponseEntity<ReferenceActivitiesDTO> coreActivities() {
        final List<CoreActivity> coreActivities = coreActivityService.coreActivities();

        final ReferenceActivitiesDTO coreActivitiesDto = new ReferenceActivitiesDTO(coreActivitiesDto(coreActivities));
        coreActivitiesDto.add(homeLink());

        return ResponseEntity.ok(coreActivitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/clusters")
    public ResponseEntity<ClustersDTO> clusters(final HttpSession httpSession) {
        final String userCountry = (String) httpSession.getAttribute(USER_COUNTRY);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(userCountry));

        final ClustersDTO clustersDto = new ClustersDTO(clustersDto(clusters));
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
