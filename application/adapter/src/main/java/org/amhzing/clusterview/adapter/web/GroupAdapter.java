package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.adapter.web.api.GroupsDTO;
import org.amhzing.clusterview.adapter.web.util.GroupDtoFactory;
import org.amhzing.clusterview.adapter.web.util.GroupFactory;
import org.amhzing.clusterview.core.boundary.enter.GroupService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class GroupAdapter {

    private GroupService groupService;

    public GroupAdapter(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    public GroupsDTO groups(final String clusterId) {
        notBlank(clusterId);

        final Set<Group> groups = groupService.groups(Cluster.Id.create(clusterId));

        return GroupDtoFactory.convertGroups(groups);
    }

    public GroupDTO group(final String obfuscatedGroupId) {
        notBlank(obfuscatedGroupId);

        final long groupId = Obfuscator.deobfuscate(obfuscatedGroupId);
        final Group group = groupService.group(Group.Id.create(groupId));

        return GroupDtoFactory.convertGroup(group);
    }

    public void create(final GroupDTO groupDto, final String clusterId) {
        notNull(groupDto);
        notBlank(clusterId);

        final Group group = GroupFactory.convert(groupDto);
        groupService.createGroup(group, Cluster.Id.create(clusterId));
    }

    public void update(final GroupDTO groupDto, final String clusterId) {
        notNull(groupDto);
        notBlank(clusterId);

        final Group group = GroupFactory.convert(groupDto);
        groupService.updateGroup(group, Cluster.Id.create(clusterId));
    }

    public void delete(final String obfuscatedGroupId, final String cluster) {
        notBlank(obfuscatedGroupId);
        notBlank(cluster);

        final long groupId = Obfuscator.deobfuscate(obfuscatedGroupId);
        groupService.deleteGroup(Group.Id.create(groupId), Cluster.Id.create(cluster));
    }
}
