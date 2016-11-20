package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.ClusterRepository;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterService implements ClusterService {

    private ClusterRepository clusterRepository;

    public DefaultClusterService(final ClusterRepository clusterRepository) {
        this.clusterRepository = notNull(clusterRepository);
    }

    @Override
    public Set<Group> groups(final String cluster) {
        notBlank(cluster);

        return clusterRepository.groups(Cluster.Id.create(cluster));
    }
}
