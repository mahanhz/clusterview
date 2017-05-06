package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.StatisticService;
import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;

import static org.apache.commons.lang3.Validate.notNull;

public class CourseStatisticService implements StatisticService<CourseStatistic> {

    private StatisticRepository<Country.Id, CourseStatistic> countryStatisticRepository;
    private StatisticRepository<Region.Id, CourseStatistic> regionStatisticRepository;
    private StatisticRepository<Cluster.Id, CourseStatistic> clusterStatisticRepository;

    public CourseStatisticService(final StatisticRepository<Country.Id, CourseStatistic> countryStatisticRepository,
                                  final StatisticRepository<Region.Id, CourseStatistic> regionStatisticRepository,
                                  final StatisticRepository<Cluster.Id, CourseStatistic> clusterStatisticRepository) {
        this.countryStatisticRepository = notNull(countryStatisticRepository);
        this.regionStatisticRepository = notNull(regionStatisticRepository);
        this.clusterStatisticRepository = notNull(clusterStatisticRepository);
    }

    @Override
    public CourseStatistic statistics(final Country.Id countryId) {
        return countryStatisticRepository.statistics(countryId);
    }

    @Override
    public CourseStatistic statistics(final Region.Id regionId) {
        return regionStatisticRepository.statistics(regionId);
    }

    @Override
    public CourseStatistic statistics(final Cluster.Id clusterId) {
        return clusterStatisticRepository.statistics(clusterId);
    }
}
