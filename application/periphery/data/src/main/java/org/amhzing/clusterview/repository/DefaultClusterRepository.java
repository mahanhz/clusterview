package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.boundary.exit.repository.ClusterRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.exception.NotFoundException;
import org.amhzing.clusterview.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.jpa.entity.CountryEntity;
import org.amhzing.clusterview.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.cache.CacheSpec.*;
import static org.amhzing.clusterview.repository.ClusterEntityFactory.courses;
import static org.amhzing.clusterview.repository.StatisticFactory.clusterEntities;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CLUSTERS_CACHE_NAME)
public class DefaultClusterRepository implements ClusterRepository {

    private CountryJpaRepository countryJpaRepository;
    private ClusterJpaRepository clusterJpaRepository;

    public DefaultClusterRepository(final CountryJpaRepository countryJpaRepository,
                                    final ClusterJpaRepository clusterJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null or #result.isEmpty()")
    public List<Cluster.Id> clusters(final Country.Id id) {
        notNull(id);

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        if (country == null) {
            return emptyList();
        }

        final Stream<ClusterEntity> clusterEntityStream = clusterEntities(country);

        return clusterEntityStream.map(clusterEntity -> Cluster.Id.create(clusterEntity.getId()))
                                  .collect(Collectors.toList());
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = STATS_COURSE_CACHE_NAME, allEntries = true) })
    public void saveCourseStats(final Cluster.Id id, final CourseStatistic courseStatistic) {
        notNull(id);
        notNull(courseStatistic);

        final ClusterEntity clusterEntity = clusterJpaRepository.findOne(id.getId());

        if (clusterEntity == null) {
            throw new NotFoundException("Cluster '" + id + "' does not exist");
        }

        clusterEntity.getCourses().clear();
        clusterEntity.getCourses().putAll(courses(courseStatistic));

        clusterJpaRepository.save(clusterEntity);
    }
}
