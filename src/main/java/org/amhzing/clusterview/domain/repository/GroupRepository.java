package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;

import java.util.Set;

public interface GroupRepository {

    Set<Group> groups(final Cluster.Id clusterId);

    Group group(final Group.Id groupId);

    // FIXME - This should not use Infra
    TeamEntity createGroup(final Group group, final Cluster.Id clusterId);

    // FIXME - This should not use Infra
    TeamEntity updateGroup(final Group group, final Cluster.Id clusterId);

    void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId);
}
