package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.CLLUSTER_STATS_HISTORY_PREFIX;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.HISTORY;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @LogExecutionTime
    @PostMapping(path = HISTORY + "/{country}/{cluster}")
    public ResponseEntity<?> saveHistory(@PathVariable final String country,
                                         @PathVariable final String cluster) {

        isTrue(isValidCluster(country, cluster), "Cluster " + cluster + " is not valid for country " + country);

        final ActivityStatistic clusterStats = statisticService.statistics(Cluster.Id.create(cluster));

        statisticHistoryService.saveHistory(Cluster.Id.create(cluster), clusterStats);

        final Link clusterStatsHistoryLink = linkTo(StatisticRestController.class).slash(HISTORY)
                                                                                  .slash(country).slash(cluster)
                                                                                  .withRel(CLLUSTER_STATS_HISTORY_PREFIX + cluster);

        return ResponseEntity.created(URI.create(clusterStatsHistoryLink.getHref())).build();
    }

    private boolean isValidCluster(final String country, final String cluster) {
        return clusterService.clusters(Country.Id.create(country)).stream()
                             .map(Cluster.Id::getId)
                             .anyMatch(c -> c.equals(cluster));
    }
}
