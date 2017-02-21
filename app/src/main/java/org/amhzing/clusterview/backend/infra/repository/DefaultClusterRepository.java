package org.amhzing.clusterview.backend.infra.repository;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;
import org.amhzing.clusterview.backend.domain.repository.ClusterRepository;
import org.amhzing.clusterview.backend.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.backend.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.backend.infra.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.backend.cache.CacheSpec.CLUSTERS_CACHE_NAME;
import static org.amhzing.clusterview.backend.infra.repository.StatisticFactory.clusterEntities;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CLUSTERS_CACHE_NAME)
public class DefaultClusterRepository implements ClusterRepository {

    private CountryJpaRepository countryJpaRepository;

    public DefaultClusterRepository(final CountryJpaRepository countryJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
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
}
