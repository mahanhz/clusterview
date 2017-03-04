package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.CourseEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.CourseJpaRepository;
import org.apache.commons.collections.MapUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.amhzing.clusterview.app.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_COURSE_CACHE_NAME)
public class ClusterCourseStatisticRepository implements StatisticRepository<Cluster.Id, CourseStatistic> {

    private ClusterJpaRepository clusterJpaRepository;
    private CourseJpaRepository courseJpaRepository;

    public ClusterCourseStatisticRepository(final ClusterJpaRepository clusterJpaRepository,
                                            final CourseJpaRepository courseJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
        this.courseJpaRepository = notNull(courseJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public CourseStatistic statistics(final Cluster.Id id) {
        final ClusterEntity cluster = clusterJpaRepository.findOne(id.getId());

        if (cluster == null) {
            return CourseStatistic.empty();
        }

        // If there are no courses then create one with a default value of 0
        if (MapUtils.isEmpty(cluster.getCourses())) {
            final List<CourseEntity> courseEntities = courseJpaRepository.findAll();

            final Map<CourseEntity, Integer> courses = courseEntities.stream()
                                                              .collect(toMap(Function.identity(), a -> 0));

            cluster.getCourses().putAll(courses);
        }

        return CourseStatistic.create(CourseStatisticFactory.courseStats(cluster));
    }
}
