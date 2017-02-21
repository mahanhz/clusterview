package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;
import org.amhzing.clusterview.backend.domain.repository.ClusterRepository;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterService implements ClusterService {

    private ClusterRepository clusterRepository;

    public DefaultClusterService(final ClusterRepository clusterRepository) {
        this.clusterRepository = notNull(clusterRepository);
    }


    @Override
    public List<Cluster.Id> clusters(final Country.Id id) {
        notNull(id);

        return clusterRepository.clusters(id);
    }
}
