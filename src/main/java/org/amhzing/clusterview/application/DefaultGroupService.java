package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupService implements GroupService {

    private GroupRepository groupRepository;

    public DefaultGroupService(final GroupRepository groupRepository) {
        this.groupRepository = notNull(groupRepository);
    }

    @Override
    public Group group(final long groupId) {
        return groupRepository.group(Group.Id.create(groupId));
    }
}
