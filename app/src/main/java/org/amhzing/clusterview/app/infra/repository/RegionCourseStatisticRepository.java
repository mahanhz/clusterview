package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.RegionEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.RegionJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.Stream;

import static org.amhzing.clusterview.app.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.amhzing.clusterview.app.infra.repository.CourseStatisticFactory.courseStats;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_COURSE_CACHE_NAME)
public class RegionCourseStatisticRepository implements StatisticRepository<Region.Id, CourseStatistic> {

    private RegionJpaRepository regionJpaRepository;

    public RegionCourseStatisticRepository(final RegionJpaRepository regionJpaRepository) {
        this.regionJpaRepository = notNull(regionJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public CourseStatistic statistics(final Region.Id id) {
        final RegionEntity region = regionJpaRepository.findOne(id.getId());

        if (region == null) {
            return CourseStatistic.empty();
        }

        final Stream<ClusterEntity> clusterEntityStream = StatisticFactory.clusterEntities(region);

        return CourseStatistic.create(courseStats(clusterEntityStream));
    }
}
