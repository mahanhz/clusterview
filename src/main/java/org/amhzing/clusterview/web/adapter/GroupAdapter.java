package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.GroupService;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.amhzing.clusterview.web.adapter.GroupModelFactory.convertGroup;
import static org.amhzing.clusterview.web.adapter.GroupModelFactory.convertGroups;
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

        return convertGroups(groups);
    }

    public GroupModel group(final long groupId) {
        final Group group = groupService.group(Group.Id.create(groupId));

        return convertGroup(group);
    }

    public void createGroup(final GroupModel groupModel, final String clusterId) {
        notNull(groupModel);
        notBlank(clusterId);

        final Group group = GroupFactory.convert(groupModel);

        groupService.createGroup(group, Cluster.Id.create(clusterId));
    }

    public void deleteGroup(final long groupId) {
        groupService.deleteGroup(Group.Id.create(groupId));
    }

    public void updateGroup(final GroupModel groupModel) {
        notNull(groupModel);

        final Group group = GroupFactory.convert(groupModel);

        groupService.updateGroup(group);
    }
}
