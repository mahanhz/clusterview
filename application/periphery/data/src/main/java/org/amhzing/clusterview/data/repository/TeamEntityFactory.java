package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.*;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.data.jpa.entity.*;
import org.amhzing.clusterview.data.jpa.repository.ActivityJpaRepository;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
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
        teamEntity.getCoreActivities().clear();
        teamEntity.getCoreActivities().putAll(convertCoreActivities(group.getCoreActivities()));
        teamEntity.setCluster(teamEntity.getCluster());

        return teamEntity;
    }

    public TeamEntity convertGroupForNewTeam(final Group group, final ClusterEntity clusterEntity) {
        notNull(group);
        notNull(clusterEntity);

        final TeamEntity teamEntity = new TeamEntity();
        teamEntity.setLocation(convertLocation(group.getLocation()));
        teamEntity.setMembers(convertMembers(group.getMembers(), teamEntity));
        teamEntity.setCoreActivities(convertCoreActivities(group.getCoreActivities()));
        teamEntity.setCluster(clusterEntity);

        return teamEntity;
    }

    private org.amhzing.clusterview.data.jpa.entity.Location convertLocation(final org.amhzing.clusterview.core.domain.Location location) {
        return org.amhzing.clusterview.data.jpa.entity.Location.create(location.coordX(), location.coordY());
    }

    private Set<MemberEntity> convertMembers(final Set<Member> members, final TeamEntity teamEntity) {
        return members.stream()
                      .map(member -> convertMember(member, teamEntity))
                      .collect(toSet());
    }

    private Map<CoreActivityEntity, ParticipantQuantity> convertCoreActivities(final Set<CoreActivity> coreActivities) {
        return coreActivities.stream()
                             .collect(toMap(this::convertCoreActivity, this::convertParticipantQuantity));
    }

    private ParticipantQuantity convertParticipantQuantity(final CoreActivity coreActivity) {
        return ParticipantQuantity.create(coreActivity.getQuantity().getValue(),
                                          coreActivity.getTotalParticipants().getValue(),
                                          coreActivity.getCommunityOfInterest().getValue());
    }

    private CoreActivityEntity convertCoreActivity(final CoreActivity coreActivity) {
        final CoreActivityEntity coreActivityEntity = new CoreActivityEntity();
        coreActivityEntity.setId(coreActivity.getId().getId());
        coreActivityEntity.setName(coreActivity.getName());

        return coreActivityEntity;
    }

    private MemberEntity convertMember(final Member member, final TeamEntity teamEntity) {
        final MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(convertName(member.getName()));
        memberEntity.setCapabilities(convertCapabilities(member.getCapability(), memberEntity));
        memberEntity.setCommitments(convertCommitments(member.getCommitment(), memberEntity));
        memberEntity.setTeam(teamEntity);

        return memberEntity;
    }

    private org.amhzing.clusterview.data.jpa.entity.Name convertName(final org.amhzing.clusterview.core.domain.Name name) {
        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return org.amhzing.clusterview.data.jpa.entity.Name.create(firstName == null ? "" : firstName.value(),
                                                                   middleName == null ? "" : middleName.value(),
                                                                   lastName == null ? "" : lastName.value(),
                                                                   suffix == null ? "" : suffix.value());
    }

    private Set<CapabilityEntity> convertCapabilities(final Capability capability, final MemberEntity memberEntity) {
        return capability.getActivities()
                         .stream()
                         .map(activity -> CapabilityEntity.create(activity(activity), memberEntity))
                         .collect(toSet());
    }

    private Set<CommitmentEntity> convertCommitments(final Commitment commitment, final MemberEntity memberEntity) {
        return commitment.getActivities()
                         .stream()
                         .map(activity -> CommitmentEntity.create(activity(activity), memberEntity))
                         .collect(toSet());
    }

    private ActivityEntity activity(final Activity activity) {
        return activityJpaRepository.findOne(activity.getId().getId());
    }
}
