package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;

import java.util.Set;

import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeam;
import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeams;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupRepository implements GroupRepository {

    private ClusterJpaRepository clusterJpaRepository;
    private TeamJpaRepository teamJpaRepository;
    private ActivityJpaRepository activityJpaRepository;

    public DefaultGroupRepository(final ClusterJpaRepository clusterJpaRepository,
                                  final TeamJpaRepository teamJpaRepository,
                                  final ActivityJpaRepository activityJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
        this.teamJpaRepository = notNull(teamJpaRepository);
        this.activityJpaRepository = notNull(activityJpaRepository);
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

        final TeamEntityFactory teamEntityFactory = TeamEntityFactory.create(clusterJpaRepository,
                                                                             activityJpaRepository);
        final TeamEntity teamEntity = teamEntityFactory.convertGroup(group, clusterId);

        return teamJpaRepository.saveAndFlush(teamEntity);
    }

    @Override
    public void deleteGroup(final Group.Id groupId) {
        notNull(groupId);

        teamJpaRepository.delete(groupId.getId());
    }
}
