package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;

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
