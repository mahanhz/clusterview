package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.StatisticService;
import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;

import static org.apache.commons.lang3.Validate.notNull;

public class ActivityStatisticService implements StatisticService<ActivityStatistic> {

    private StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository;
    private StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository;
    private StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository;

    public ActivityStatisticService(final StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository,
                                    final StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository,
                                    final StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository) {
        this.countryStatisticRepository = notNull(countryStatisticRepository);
        this.regionStatisticRepository = notNull(regionStatisticRepository);
        this.clusterStatisticRepository = notNull(clusterStatisticRepository);
    }

    @Override
    public ActivityStatistic statistics(final Country.Id countryId) {
        return countryStatisticRepository.statistics(countryId);
    }

    @Override
    public ActivityStatistic statistics(final Region.Id regionId) {
        return regionStatisticRepository.statistics(regionId);
    }

    @Override
    public ActivityStatistic statistics(final Cluster.Id clusterId) {
        return clusterStatisticRepository.statistics(clusterId);
    }
}
