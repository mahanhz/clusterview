package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Course;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.domain.repository.CourseStatisticRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.amhzing.clusterview.app.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_COURSE_CACHE_NAME)
public class ClusterCourseStatisticRepository implements CourseStatisticRepository<Cluster.Id> {

    private ClusterJpaRepository clusterJpaRepository;

    public ClusterCourseStatisticRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public CourseStatistic statistics(final Cluster.Id id) {
        final ClusterEntity cluster = clusterJpaRepository.findOne(id.getId());

        if (cluster == null) {
            return CourseStatistic.empty();
        }

        return CourseStatistic.create(CourseStatisticFactory.courseStats(cluster));
    }
}
