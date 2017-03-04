package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.ClusterRepository;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.cache.CacheSpec.CLUSTERS_CACHE_NAME;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.amhzing.clusterview.app.infra.repository.ClusterEntityFactory.courses;
import static org.amhzing.clusterview.app.infra.repository.StatisticFactory.clusterEntities;
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
    @Cacheable(key= "#root.caches[0].name", unless = "#result == null or #result.isEmpty()")
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
