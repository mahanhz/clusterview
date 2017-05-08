package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.StatisticAdapter;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.CLUSTER_STATS_HISTORY_PREFIX;
import static org.amhzing.clusterview.web.controller.appnav.StatisticRestController.HISTORY;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/statsedit", produces = APPLICATION_JSON_V1_VALUE)
public class StatisticEditRestController {

    private StatisticAdapter statisticAdapter;
    private ClusterAdapter clusterAdapter;

    @Autowired
    public StatisticEditRestController(final StatisticAdapter statisticAdapter,
                                       final ClusterAdapter clusterAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
        this.clusterAdapter = notNull(clusterAdapter);
    }

    @LogExecutionTime
    @PostMapping(path = HISTORY + "/{country}/{cluster}")
    public ResponseEntity<?> saveHistory(@PathVariable final String country,
                                         @PathVariable final String cluster) {

        isTrue(isValidCluster(country, cluster), "Cluster " + cluster + " is not valid for country " + country);

        statisticAdapter.saveHistory(cluster);

        final Link clusterStatsHistoryLink = linkTo(methodOn(StatisticRestController.class).clusterHistory(country, cluster)).withRel(CLUSTER_STATS_HISTORY_PREFIX + cluster);

        return ResponseEntity.created(URI.create(clusterStatsHistoryLink.getHref())).build();
    }

    private boolean isValidCluster(final String country, final String cluster) {
        return clusterAdapter.clusters(country).clusters.stream()
                             .map(clusterDTO -> clusterDTO.name)
                             .anyMatch(c -> c.equals(cluster));
    }
}
