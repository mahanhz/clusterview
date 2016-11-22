package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;

import static org.amhzing.clusterview.infra.repository.RepositoryFactory.convertGroup;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupRepository implements GroupRepository {

    private TeamJpaRepository teamJpaRepository;

    public DefaultGroupRepository(final TeamJpaRepository teamJpaRepository) {
        this.teamJpaRepository = notNull(teamJpaRepository);
    }

    @Override
    public Group group(final Group.Id groupId) {
        notNull(groupId);

        final TeamEntity team = teamJpaRepository.findOne(groupId.getId());

        return convertGroup(team);
    }
}
