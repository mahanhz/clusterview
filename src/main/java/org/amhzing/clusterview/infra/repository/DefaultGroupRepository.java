package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;

import java.util.Set;

import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeam;
import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeams;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupRepository implements GroupRepository {

    private TeamJpaRepository teamJpaRepository;
    private ClusterJpaRepository clusterJpaRepository;

    public DefaultGroupRepository(final TeamJpaRepository teamJpaRepository,
                                  final ClusterJpaRepository clusterJpaRepository) {
        this.teamJpaRepository = notNull(teamJpaRepository);
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    public Set<Group> groups(final Cluster.Id clusterId) {
        notNull(clusterId);

        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());

        return convertTeams(cluster.getTeams());
    }

    @Override
    public Group group(final Group.Id groupId) {
        notNull(groupId);

        final TeamEntity team = teamJpaRepository.findOne(groupId.getId());

        return convertTeam(team);
    }

    @Override
    public TeamEntity createGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());

        final TeamEntity teamEntity = TeamEntityFactory.convertGroup(group, cluster);

        return teamJpaRepository.saveAndFlush(teamEntity);
    }
}
