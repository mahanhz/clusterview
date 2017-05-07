package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.data.cache.CacheSpec;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.entity.CountryEntity;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.Stream;

import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CacheSpec.STATS_COURSE_CACHE_NAME)
public class CountryCourseStatisticRepository implements StatisticRepository<Country.Id, CourseStatistic> {

    private CountryJpaRepository countryJpaRepository;

    public CountryCourseStatisticRepository(final CountryJpaRepository countryJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
    }

    @Override
    @Cacheable(key= CacheSpec.DEFAULT_CACHE_KEY, unless = "#result == null")
    public CourseStatistic statistics(final Country.Id id) {
        notNull(id);

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        if (country == null) {
            return CourseStatistic.empty();
        }

        final Stream<ClusterEntity> clusterEntityStream = StatisticFactory.clusterEntities(country);

        return CourseStatistic.create(CourseStatisticFactory.courseStats(clusterEntityStream));
    }
}
