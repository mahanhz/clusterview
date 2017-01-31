package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;

import java.util.Set;

public interface GroupRepository {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    Group createGroup(final Group group, final Cluster.Id clusterId);

    Group updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
