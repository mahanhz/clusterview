package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.appui.web.model.GroupModel;
import org.amhzing.clusterview.core.boundary.enter.GroupService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class GroupAdapter {

    private GroupService groupService;

    @Autowired
    public GroupAdapter(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    public Set<GroupModel> groups(final String clusterId) {
        notBlank(clusterId);

        final Set<Group> groups = groupService.groups(Cluster.Id.create(clusterId));

        return GroupModelFactory.convertGroups(groups);
    }

    public GroupModel group(final String obfuscatedId) {
        notBlank(obfuscatedId);

        final long groupId = Obfuscator.deobfuscate(obfuscatedId);
        final Group group = groupService.group(Group.Id.create(groupId));

        return GroupModelFactory.convertGroup(group);
    }

    public void createGroup(final GroupModel groupModel, final String clusterId) {
        notNull(groupModel);
        notBlank(clusterId);

        final Group group = GroupFactory.convert(groupModel);

        groupService.createGroup(group, Cluster.Id.create(clusterId));
    }

    public void deleteGroup(final String obfuscatedId, final String clusterId) {
        notBlank(obfuscatedId);
        notBlank(clusterId);

        final long groupId = Obfuscator.deobfuscate(obfuscatedId);
        groupService.deleteGroup(Group.Id.create(groupId), Cluster.Id.create(clusterId));
    }

    public void updateGroup(final GroupModel groupModel, final String clusterId) {
        notNull(groupModel);
        notBlank(clusterId);

        final Group group = GroupFactory.convert(groupModel);

        groupService.updateGroup(group, Cluster.Id.create(clusterId));
    }
}
