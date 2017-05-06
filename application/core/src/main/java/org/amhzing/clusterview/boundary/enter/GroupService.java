package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;

import java.util.Set;

public interface GroupService {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    void createGroup(final Group group, final Cluster.Id clusterId);

    void updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
