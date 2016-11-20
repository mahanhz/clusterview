package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;

import java.util.List;

public interface ClusterRepository {

    List<TeamEntity> teams(final Cluster.Id clusterId);
}
