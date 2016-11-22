package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import java.util.Set;

import static org.amhzing.clusterview.infra.repository.RepositoryFactory.convertTeams;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterRepository implements ClusterRepository {

    private ClusterJpaRepository clusterJpaRepository;

    public DefaultClusterRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    public Set<Group> groups(final Cluster.Id clusterId) {
        notNull(clusterId);

        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());

        return convertTeams(cluster.getTeams());
    }
}
