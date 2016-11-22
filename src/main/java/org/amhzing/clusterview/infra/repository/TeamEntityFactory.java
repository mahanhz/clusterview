package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.mapping.Location;
import org.amhzing.clusterview.infra.jpa.mapping.Name;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class TeamEntityFactory {

    private ClusterJpaRepository clusterJpaRepository;
    private ActivityJpaRepository activityJpaRepository;

    private TeamEntityFactory(final ClusterJpaRepository clusterJpaRepository,
                              final ActivityJpaRepository activityJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
        this.activityJpaRepository = notNull(activityJpaRepository);
    }

    public static TeamEntityFactory create(final ClusterJpaRepository clusterJpaRepository,
                                           final ActivityJpaRepository activityJpaRepository) {
        return new TeamEntityFactory(clusterJpaRepository, activityJpaRepository);
    }


    public TeamEntity convertGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final ClusterEntity clusterEntity = clusterJpaRepository.findOne(clusterId.getId());

        final TeamEntity teamEntity = new TeamEntity();
        teamEntity.setLocation(convertLocation(group.getLocation()));
        teamEntity.setMembers(convertMembers(group.getMembers(), teamEntity));
        teamEntity.setCluster(clusterEntity);

        return teamEntity;
    }

    private Location convertLocation(final org.amhzing.clusterview.domain.model.Location location) {
        return Location.create(location.getCoordX(), location.getCoordY());
    }

    private Set<MemberEntity> convertMembers(final Set<Member> members, final TeamEntity teamEntity) {
        return members.stream()
                      .map(member -> convertMember(member, teamEntity))
                      .collect(Collectors.toSet());
    }

    private MemberEntity convertMember(final Member member, final TeamEntity teamEntity) {
        final MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(convertName(member.getName()));
        memberEntity.setCapabilities(convertCapabilities(member.getCapability(), memberEntity));
        memberEntity.setCommitments(convertCommitments(member.getCommitment(), memberEntity));
        memberEntity.setTeam(teamEntity);

        return memberEntity;
    }

    private Name convertName(final org.amhzing.clusterview.domain.model.Name name) {
        return Name.create(name.getFirstName().getValue(),
                           name.getMiddleName().getValue(),
                           name.getLastName().getValue(),
                           name.getSuffix().getValue());
    }

    private Set<CapabilityEntity> convertCapabilities(final Capability capability, final MemberEntity memberEntity) {
        return capability.getActivities()
                         .stream()
                         .map(activity -> CapabilityEntity.create(activity(activity), memberEntity))
                         .collect(Collectors.toSet());
    }

    private Set<CommitmentEntity> convertCommitments(final Commitment commitment, final MemberEntity memberEntity) {
        return commitment.getActivities()
                         .stream()
                         .map(activity -> CommitmentEntity.create(activity(activity), memberEntity))
                         .collect(Collectors.toSet());
    }

    private ActivityEntity activity(final Activity activity) {
        return activityJpaRepository.findOne(activity.getId().getId());
    }
}
