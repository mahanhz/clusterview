package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Group;
import org.amhzing.clusterview.app.domain.model.Cluster;

import java.util.Set;

public interface GroupService {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    void createGroup(final Group group, final Cluster.Id clusterId);

    void updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
