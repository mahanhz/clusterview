package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.infra.repository.StatisticFactory.clusterEntities;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterRepository implements ClusterRepository {

    private CountryJpaRepository countryJpaRepository;

    public DefaultClusterRepository(final CountryJpaRepository countryJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
    }

    @Override
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
