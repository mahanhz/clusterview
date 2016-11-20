package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterRepository implements ClusterRepository {

    private ClusterJpaRepository clusterJpaRepository;

    public DefaultClusterRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    public List<TeamEntity> teams(final Cluster.Id clusterId) {
        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());
        return null;
    }
}
