package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;

import java.util.Set;

public interface ClusterRepository {

    Set<Group> groups(final Cluster.Id clusterId);
}
