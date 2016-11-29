package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.StatisticService;
import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StatisticAdapter {

    private StatisticService statisticService;

    @Autowired
    public StatisticAdapter(final StatisticService statisticService) {
        this.statisticService = notNull(statisticService);
    }

    public ActivityStatisticModel countryStats(final String countryId) {
        notBlank(countryId);

        final ActivityStatistic statistics = statisticService.statistics(Country.Id.create(countryId));

        return ActivityStatisticModel.create(activityQuantities(statistics));
    }

    public ActivityStatisticModel regionStats(final String regionId) {
        notBlank(regionId);

        final ActivityStatistic statistics = statisticService.statistics(Region.Id.create(regionId));

        return ActivityStatisticModel.create(activityQuantities(statistics));
    }

    public ActivityStatisticModel clusterStats(final String clusterId) {
        notBlank(clusterId);

        final ActivityStatistic statistics = statisticService.statistics(Cluster.Id.create(clusterId));

        return ActivityStatisticModel.create(activityQuantities(statistics));
    }

    private Map<String, Long> activityQuantities(final ActivityStatistic statistics) {
        return statistics.getActivityQuantity()
                         .entrySet()
                         .stream()
                         .collect(toMap(entry -> entry.getKey().getName(),
                                        entry -> entry.getValue().getValue()));
    }
}
