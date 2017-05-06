package org.amhzing.clusterview.controller.appnav;

import org.amhzing.clusterview.boundary.enter.ClusterService;
import org.amhzing.clusterview.boundary.enter.StatisticHistoryService;
import org.amhzing.clusterview.boundary.enter.StatisticService;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.amhzing.clusterview.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.controller.appnav.CommonLinks.CLUSTER_STATS_HISTORY_PREFIX;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.HISTORY;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = BASE_PATH + "/statsedit", produces = APPLICATION_JSON_V1_VALUE)
public class StatisticEditRestController {

    private StatisticHistoryService statisticHistoryService;
    private StatisticService<ActivityStatistic> statisticService;
    private ClusterService clusterService;

    @Autowired
    public StatisticEditRestController(final StatisticHistoryService statisticHistoryService,
                                       final StatisticService<ActivityStatistic> statisticService,
                                       final ClusterService clusterService) {
        this.statisticHistoryService = notNull(statisticHistoryService);
        this.statisticService = notNull(statisticService);
        this.clusterService = notNull(clusterService);
    }

    @PostMapping(path = HISTORY + "/{country}/{cluster}")
    public ResponseEntity<?> saveHistory(@PathVariable final String country,
                                         @PathVariable final String cluster) {

        isTrue(isValidCluster(country, cluster), "Cluster " + cluster + " is not valid for country " + country);

        final ActivityStatistic clusterStats = statisticService.statistics(Cluster.Id.create(cluster));

        statisticHistoryService.saveHistory(Cluster.Id.create(cluster), clusterStats);

        final Link clusterStatsHistoryLink = linkTo(methodOn(StatisticRestController.class).clusterHistory(country, cluster)).withRel(CLUSTER_STATS_HISTORY_PREFIX + cluster);

        return ResponseEntity.created(URI.create(clusterStatsHistoryLink.getHref())).build();
    }

    private boolean isValidCluster(final String country, final String cluster) {
        return clusterService.clusters(Country.Id.create(country)).stream()
                             .map(Cluster.Id::getId)
                             .anyMatch(c -> c.equals(cluster));
    }
}
