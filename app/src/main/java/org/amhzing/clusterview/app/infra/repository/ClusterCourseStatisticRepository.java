package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import static org.amhzing.clusterview.app.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_COURSE_CACHE_NAME)
public class ClusterCourseStatisticRepository implements StatisticRepository<Cluster.Id, CourseStatistic> {

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
