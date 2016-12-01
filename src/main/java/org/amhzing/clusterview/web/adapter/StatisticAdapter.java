package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.ActivityService;
import org.amhzing.clusterview.application.StatisticService;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.CoreActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StatisticAdapter {

    private StatisticService statisticService;
    private ActivityService activityService;

    @Autowired
    public StatisticAdapter(final StatisticService statisticService,
                            final ActivityService activityService) {
        this.statisticService = notNull(statisticService);
        this.activityService = notNull(activityService);
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

    private Map<String, Long> committedActivityQuantities(final ActivityStatistic statistics) {
        return statistics.getActivityQuantity()
                         .entrySet()
                         .stream()
                         .collect(toMap(entry -> entry.getKey().getName(),
                                        entry -> entry.getValue().getValue()));
    }

    private Map<String, Long> activityQuantities(final ActivityStatistic statistics) {
        final Map<String, Long> committedActivityQuantities = committedActivityQuantities(statistics);

        // Add the remainder of activities with a quantity of 0
        activityService.activities()
                       .stream()
                       .forEach(activity -> committedActivityQuantities.computeIfAbsent(activity.getName(), a -> 0L));

        // return a sorted map
        return new TreeMap<>(committedActivityQuantities);
    }

    public List<CoreActivityModel> coreActivities(final ActivityStatistic statistics) {
        final Set<CoreActivity> coreActivities = statistics.getCoreActivities();

        return coreActivities.stream()
                             .map(coreActivity -> coreActivityModel(coreActivity))
                             .sorted((a1, a2) -> a1.getName().compareTo(a2.getName()))
                             .collect(Collectors.toList());
    }

    private static CoreActivityModel coreActivityModel(final CoreActivity coreActivity) {
        return CoreActivityModel.create(coreActivity.getId().getId(),
                                        coreActivity.getName(),
                                        coreActivity.getTotalParticipants().getValue(),
                                        coreActivity.getCommunityOfInterest().getValue());
    }
}
