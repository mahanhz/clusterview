package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.ActivityAdapter;
import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.api.ClustersDTO;
import org.amhzing.clusterview.adapter.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.homeLink;
import static org.amhzing.clusterview.web.controller.util.UserUtil.USER_COUNTRY;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/referencedata", produces = APPLICATION_JSON_V1_VALUE)
public class ReferenceDataRestController {

    private ActivityAdapter activityAdapter;
    private ClusterAdapter clusterAdapter;

    @Autowired
    public ReferenceDataRestController(final ActivityAdapter activityAdapter,
                                       final ClusterAdapter clusterAdapter) {
        this.activityAdapter = notNull(activityAdapter);
        this.clusterAdapter = notNull(clusterAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/activities")
    public ResponseEntity<Resource<ReferenceActivitiesDTO>> activities() {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(ReferenceDataRestController.class).activities());

        final Resource<ReferenceActivitiesDTO> activitiesDto = new Resource<>(activityAdapter.activities());
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/coreactivities")
    public ResponseEntity<Resource<ReferenceActivitiesDTO>> coreActivities() {
        final ControllerLinkBuilder selfLink = linkTo(methodOn(ReferenceDataRestController.class).coreActivities());

        final Resource<ReferenceActivitiesDTO> activitiesDto = new Resource<>(activityAdapter.coreActivities());
        activitiesDto.add(selfLink.withSelfRel());
        activitiesDto.add(homeLink());

        return ResponseEntity.ok(activitiesDto);
    }

    @LogExecutionTime
    @GetMapping(path = "/clusters")
    public ResponseEntity<Resource<ClustersDTO>> clusters(final HttpServletRequest request) {
        final String userCountry = (String) request.getAttribute(USER_COUNTRY);

        final ControllerLinkBuilder selfLink = linkTo(ReferenceDataRestController.class).slash("/clusters");

        final Resource<ClustersDTO> clustersDto = new Resource<>(clusterAdapter.clusters(userCountry));
        clustersDto.add(selfLink.withSelfRel());
        clustersDto.add(homeLink());

        return ResponseEntity.ok(clustersDto);
    }
}
