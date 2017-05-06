package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;

import java.util.Set;

public interface GroupService {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    void createGroup(final Group group, final Cluster.Id clusterId);

    void updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
