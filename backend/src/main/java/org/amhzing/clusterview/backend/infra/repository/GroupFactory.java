package org.amhzing.clusterview.backend.infra.repository;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.backend.domain.model.*;
import org.amhzing.clusterview.backend.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.backend.domain.model.statistic.Quantity;
import org.amhzing.clusterview.backend.infra.jpa.mapping.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupFactory {

    private GroupFactory() {
        // To prevent instantiation
    }

    public static Set<Group> convertTeams(final Set<TeamEntity> teams) {
        noNullElements(teams);

        return teams.stream()
                    .map(GroupFactory::convertTeam)
                    .collect(Collectors.toSet());
    }

    public static Group convertTeam(final TeamEntity team) {
        notNull(team);

        return Group.create(Group.Id.create(team.getId()),
                            convertMembers(team.getMembers()),
                            convertLocation(team.getLocation()),
                            convertCoreActivities(team.getCoreActivities()));
    }

    private static org.amhzing.clusterview.backend.domain.model.Location convertLocation(final org.amhzing.clusterview.backend.infra.jpa.mapping.Location location) {
        return ImmutableLocation.of(location.getX(), location.getY());
    }

    private static Set<CoreActivity> convertCoreActivities(final Map<CoreActivityEntity, ParticipantQuantity> coreActivityQuantities) {
        return coreActivityQuantities.entrySet().stream()
                                     .map(GroupFactory::convertCoreActivity)
                                     .collect(Collectors.toSet());
    }

    private static CoreActivity convertCoreActivity(final Map.Entry<CoreActivityEntity, ParticipantQuantity> entry) {
        final CoreActivityEntity activity = entry.getKey();
        final ParticipantQuantity quantity = entry.getValue();

        return CoreActivity.create(CoreActivity.Id.create(activity.getId()),
                                   activity.getName(),
                                   Quantity.create(quantity.getNumActivities()),
                                   Quantity.create(quantity.getTotal()),
                                   Quantity.create(quantity.getCommunityOfInterest()));
    }

    private static Set<Member> convertMembers(final Set<MemberEntity> members) {
        return members.stream()
                      .map(GroupFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static Member convertMember(final MemberEntity member) {
        return Member.create(Member.Id.create(member.getId()),
                             convertName(member.getName()),
                             convertCapabilities(member.getCapabilities()),
                             convertCommitments(member.getCommitments()));
    }

    private static org.amhzing.clusterview.backend.domain.model.Name convertName(final org.amhzing.clusterview.backend.infra.jpa.mapping.Name name) {
        final String firstName = name.getFirstName();
        final String middleName = StringUtils.defaultIfBlank(name.getMiddleName(), "");
        final String lastName = name.getLastName();
        final String suffix = StringUtils.defaultIfBlank(name.getSuffix(), "");

        return ImmutableName.builder()
                            .firstName(ImmutableFirstName.of(firstName))
                            .middleName(ImmutableMiddleName.of(middleName))
                            .lastName(ImmutableLastName.of(lastName))
                            .suffix(ImmutableSuffix.of(suffix))
                            .build();
    }

    private static Capability convertCapabilities(final Set<CapabilityEntity> capabilities) {
        final Set<Activity> activities = capabilities.stream()
                                                     .map(capability -> activity(capability.getActivity()))
                                                     .collect(Collectors.toSet());

        return Capability.create(activities);
    }

    private static Commitment convertCommitments(final Set<CommitmentEntity> commitments) {
        final Set<Activity> activities = commitments.stream()
                                                    .map(commitment -> activity(commitment.getActivity()))
                                                    .collect(Collectors.toSet());

        return Commitment.create(activities);
    }

    private static Activity activity(final ActivityEntity activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }
}
