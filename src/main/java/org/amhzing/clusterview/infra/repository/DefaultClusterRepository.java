package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.mapping.Location;
import org.amhzing.clusterview.infra.jpa.mapping.Name;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterRepository implements ClusterRepository {

    private ClusterJpaRepository clusterJpaRepository;

    public DefaultClusterRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    public Set<Group> groups(final Cluster.Id clusterId) {
        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());

        return convertTeams(cluster.getTeams());
    }

    private Set<Group> convertTeams(final Set<TeamEntity> teams) {
        return teams.stream()
                    .map(this::createGroup)
                    .collect(Collectors.toSet());
    }

    private Group createGroup(final TeamEntity team) {
        return Group.create(Group.Id.create(new Long(team.getId()).toString()),
                            convertMembers(team.getMembers()),
                            convertLocation(team.getLocation()));
    }

    private org.amhzing.clusterview.domain.model.Location convertLocation(final Location location) {
        return org.amhzing.clusterview.domain.model.Location.create(location.getX(), location.getY());
    }

    private Set<Member> convertMembers(final Set<MemberEntity> members) {
        return members.stream()
                      .map(this::createMember)
                      .collect(Collectors.toSet());
    }

    private Member createMember(final MemberEntity member) {
        return Member.create(Member.Id.create(new Long(member.getId()).toString()),
                             convertName(member.getName()),
                             convertCapabilities(member.getCapabilities()),
                             convertCommitments(member.getCommitments()));
    }

    private org.amhzing.clusterview.domain.model.Name convertName(final Name name) {
        return org.amhzing.clusterview.domain.model.Name.create(FirstName.create(name.getFirstName()),
                                                                MiddleName.create(name.getMiddleName()),
                                                                LastName.create(name.getLastName()),
                                                                Suffix.create(name.getSuffix()));
    }

    private Capability convertCapabilities(final Set<CapabilityEntity> capabilities) {
        final Set<Activity> activities = capabilities.stream()
                                                     .map(capability -> activity(capability.getActivity()))
                                                     .collect(Collectors.toSet());

        return Capability.create(activities);
    }

    private Commitment convertCommitments(final Set<CommitmentEntity> commitments) {
        final Set<Activity> activities = commitments.stream()
                                                    .map(commitment -> activity(commitment.getActivity()))
                                                    .collect(Collectors.toSet());

        return Commitment.create(activities);
    }

    private Activity activity(final ActivityEntity activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }
}
