package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.cache.CacheSpec;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.CourseStatisticRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.Stream;

import static org.amhzing.clusterview.app.infra.repository.CourseStatisticFactory.courseStats;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CacheSpec.STATS_COURSE_CACHE_NAME)
public class CountryCourseStatisticRepository implements CourseStatisticRepository<Country.Id> {

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

        return CourseStatistic.create(courseStats(clusterEntityStream));
    }
}
