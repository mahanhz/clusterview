package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.GroupService;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.amhzing.clusterview.web.adapter.AdapterFactory.convertGroup;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class GroupAdapter {

    private GroupService groupService;

    @Autowired
    public GroupAdapter(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    public GroupModel group(final long groupId) {
        final Group group = groupService.group(groupId);

        return convertGroup(group);
    }
}
