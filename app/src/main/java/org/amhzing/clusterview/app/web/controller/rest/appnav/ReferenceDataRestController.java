package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.api.ClusterDTO;
import org.amhzing.clusterview.app.api.ClustersDTO;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.app.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityModel;
import org.amhzing.clusterview.app.web.model.CoreActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController.USER_COUNTRY;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping(path = BASE_PATH + "/referencedata", produces = APPLICATION_JSON_V1_VALUE)
public class ReferenceDataRestController {

    private StatisticAdapter statisticAdapter;
    private ActivityAdapter activityAdapter;
    private CoreActivityAdapter coreActivityAdapter;
    private ClusterService clusterService;

    @Autowired
    public ReferenceDataRestController(final StatisticAdapter statisticAdapter,
                                       final ActivityAdapter activityAdapter,
                                       final CoreActivityAdapter coreActivityAdapter,
                                       final ClusterService clusterService) {
        this.statisticAdapter = notNull(statisticAdapter);
        this.activityAdapter = notNull(activityAdapter);
        this.coreActivityAdapter = notNull(coreActivityAdapter);
        this.clusterService = notNull(clusterService);
    }

    @LogExecutionTime
    @GetMapping(path = "/activities")
    public List<ActivityModel> activities() {
        return activityAdapter.activities();
    }

    @LogExecutionTime
    @GetMapping(path = "/coreactivities")
    public List<CoreActivityModel> coreActivities() {
        return coreActivityAdapter.coreActivities();
    }

    @LogExecutionTime
    @GetMapping(path = "/clusters")
    public ResponseEntity<ClustersDTO> clusters(final HttpSession httpSession) {
        final String userCountry = (String) httpSession.getAttribute(USER_COUNTRY);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(userCountry));

        final List<ClusterDTO> clusterDtos = clusters.stream()
                                                     .map(cluster -> new ClusterDTO(cluster.getId()))
                                                     .sorted(comparing(cluster -> cluster.name))
                                                     .collect(toList());

        return ResponseEntity.ok(new ClustersDTO(clusterDtos));
    }
}
