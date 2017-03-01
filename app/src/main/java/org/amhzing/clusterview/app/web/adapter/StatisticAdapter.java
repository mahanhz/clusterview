package org.amhzing.clusterview.app.web.adapter;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.ClusterNameModel;
import org.amhzing.clusterview.app.web.model.DatedActivityStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.app.web.adapter.StatisticFactory.*;
import static org.amhzing.clusterview.app.web.adapter.StatisticFactory.PARTICIPANT;
import static org.amhzing.clusterview.app.web.adapter.StatisticFactory.coreActivities;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StatisticAdapter {

    private StatisticService<ActivityStatistic> statisticService;
    private StatisticHistoryService statisticHistoryService;
    private ActivityService activityService;
    private ClusterService clusterService;

    @Autowired
    public StatisticAdapter(final StatisticService statisticService,
                            final StatisticHistoryService statisticHistoryService,
                            final ActivityService activityService,
                            final ClusterService clusterService) {
        this.statisticService = notNull(statisticService);
        this.statisticHistoryService = notNull(statisticHistoryService);
        this.activityService = notNull(activityService);
        this.clusterService = notNull(clusterService);
    }

    public ActivityStatisticModel countryStats(final String countryId) {
        notBlank(countryId);

        final ActivityStatistic statistics = statisticService.statistics(Country.Id.create(countryId));


        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public ActivityStatisticModel regionStats(final String regionId) {
        notBlank(regionId);

        final ActivityStatistic statistics = statisticService.statistics(Region.Id.create(regionId));

        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public ActivityStatisticModel clusterStats(final String clusterId) {
        notBlank(clusterId);

        final ActivityStatistic statistics = statisticService.statistics(Cluster.Id.create(clusterId));

        return ActivityStatisticModel.create(activityQuantities(statistics),
                                             coreActivities(statistics));
    }

    public List<ClusterNameModel> clusters(final String countryId) {
        notBlank(countryId);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(countryId));

        return clusters.stream()
                       .map(cluster -> ClusterNameModel.create(cluster.getId()))
                       .sorted((a,b) -> a.getName().compareTo(b.getName()))
                       .collect(Collectors.toList());
    }

    public List<DatedActivityStatisticModel> statsHistory(final String clusterId) {
        notBlank(clusterId);

        final List<DatedActivityStatistic> datedStats = statisticHistoryService.history(Cluster.Id.create(clusterId));

        final List<DatedActivityStatisticModel> collectedHistoryStats =
                datedStats.stream()
                          .map(stat -> DatedActivityStatisticModel.create(stat.getDate(), activityStatisticModel(stat)))
                          .collect(Collectors.toList());

        collectedHistoryStats.sort(Comparator.comparing(DatedActivityStatisticModel::getDate));

        return collectedHistoryStats;
    }

    public void saveStatsHistory(final String clusterId, final ActivityStatisticModel activityStatisticModel) {
        notBlank(clusterId);
        notNull(activityStatisticModel);

        statisticHistoryService.saveHistory(Cluster.Id.create(clusterId), activityStatistic(activityStatisticModel));
    }

    private ActivityStatisticModel activityStatisticModel(final DatedActivityStatistic stat) {
        return ActivityStatisticModel.create(activityQuantities(stat.getActivityStatistic()),
                                             coreActivities(stat.getActivityStatistic()));
    }

    private Map<String, Long> activityQuantities(final ActivityStatistic statistics) {
        final Map<String, Long> committedActivityQuantities = committedActivityQuantities(statistics);

        // Add the remainder of activities with a quantity of 0
        activityService.activities()
                       .stream()
                       .filter(activity -> !StringUtils.containsIgnoreCase(activity.getName(), PARTICIPANT))
                       .forEach(activity -> committedActivityQuantities.computeIfAbsent(activity.getName(), a -> 0L));

        // return a sorted map
        return new TreeMap<>(committedActivityQuantities);
    }
}
