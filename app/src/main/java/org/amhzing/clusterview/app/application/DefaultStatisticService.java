package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultStatisticService implements StatisticService {

    private StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository;
    private StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository;
    private StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository;

    public DefaultStatisticService(final StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository,
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
