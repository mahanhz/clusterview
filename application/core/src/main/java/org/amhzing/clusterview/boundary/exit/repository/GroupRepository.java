package org.amhzing.clusterview.boundary.exit.repository;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;

import java.util.Set;

public interface GroupRepository {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    Group createGroup(final Group group, final Cluster.Id clusterId);

    Group updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
