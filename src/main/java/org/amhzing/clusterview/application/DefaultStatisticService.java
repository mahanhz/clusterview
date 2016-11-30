package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultStatisticService implements StatisticService {

    private CountryStatisticRepository countryStatisticRepository;
    private RegionStatisticRepository regionStatisticRepository;
    private ClusterStatisticRepository clusterStatisticRepository;

    public DefaultStatisticService(final CountryStatisticRepository countryStatisticRepository,
                                   final RegionStatisticRepository regionStatisticRepository,
                                   final ClusterStatisticRepository clusterStatisticRepository) {
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
