package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;

import java.util.Set;

public interface GroupService {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    void createGroup(final Group group, final Cluster.Id clusterId);

    void updateGroup(final Group group);

    void deleteGroup(final Group.Id groupId);
}
