package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.GroupService;
import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupService implements GroupService {

    private GroupRepository groupRepository;

    public DefaultGroupService(final GroupRepository groupRepository) {
        this.groupRepository = notNull(groupRepository);
    }

    @Override
    public Set<Group> groups(final Cluster.Id clusterId) {
        notNull(clusterId);

        return groupRepository.groups(clusterId);
    }

    @Override
    public Group group(final Group.Id groupId) {
        notNull(groupId);

        return groupRepository.group(groupId);
    }

    @Override
    public void createGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        groupRepository.createGroup(group, clusterId);
    }

    @Override
    public void updateGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        groupRepository.updateGroup(group, clusterId);
    }

    @Override
    public void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId) {
        notNull(groupId);
        notNull(clusterId);

        groupRepository.deleteGroup(groupId, clusterId);
    }
}
