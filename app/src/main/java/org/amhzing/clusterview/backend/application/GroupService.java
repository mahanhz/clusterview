package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.Group;
import org.amhzing.clusterview.backend.domain.model.Cluster;

import java.util.Set;

public interface GroupService {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    void createGroup(final Group group, final Cluster.Id clusterId);

    void updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
