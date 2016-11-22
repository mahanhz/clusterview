package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Group;

public interface GroupRepository {

    Group group(final Group.Id groupId);
}
