package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.repository.RegionJpaRepository;
import org.amhzing.clusterview.data.jpa.entity.RegionEntity;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.Stream;

import static org.amhzing.clusterview.data.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.data.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.amhzing.clusterview.data.repository.CourseStatisticFactory.courseStats;
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

        return CourseStatistic.create(CourseStatisticFactory.courseStats(clusterEntityStream));
    }
}
