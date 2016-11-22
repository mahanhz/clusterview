package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.mapping.Location;
import org.amhzing.clusterview.infra.jpa.mapping.Name;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class TeamEntityFactory {

    private ActivityJpaRepository activityJpaRepository;

    private TeamEntityFactory(final ActivityJpaRepository activityJpaRepository) {
        this.activityJpaRepository = notNull(activityJpaRepository);
    }

    public static TeamEntityFactory create(final ActivityJpaRepository activityJpaRepository) {
        return new TeamEntityFactory(activityJpaRepository);
    }

    public TeamEntity convertGroupForExistingTeam(final Group group, final TeamEntity teamEntity) {
        notNull(group);
        notNull(teamEntity);

        teamEntity.setId(teamEntity.getId());
        teamEntity.setLocation(convertLocation(group.getLocation()));
        teamEntity.getMembers().clear();
        teamEntity.getMembers().addAll(convertMembers(group.getMembers(), teamEntity));
        teamEntity.setCluster(teamEntity.getCluster());

        return teamEntity;
    }

    public TeamEntity convertGroupForNewTeam(final Group group, final ClusterEntity clusterEntity) {
        notNull(group);
        notNull(clusterEntity);

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
        final FirstName firstName = name.getFirstName();
        final MiddleName middleName = name.getMiddleName();
        final LastName lastName = name.getLastName();
        final Suffix suffix = name.getSuffix();

        return Name.create(firstName == null ? "" : firstName.getValue(),
                           middleName == null ? "" : middleName.getValue(),
                           lastName == null ? "" : lastName.getValue(),
                           suffix == null ? "" : suffix.getValue());
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