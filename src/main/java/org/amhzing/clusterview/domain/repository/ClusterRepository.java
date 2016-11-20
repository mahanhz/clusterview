package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.infra.jpa.mapping.Team;

import java.util.List;

public interface ClusterRepository {

    List<Team> teams(final Cluster.Id clusterId);
}
