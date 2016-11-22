package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.mapping.Location;
import org.amhzing.clusterview.infra.jpa.mapping.Name;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;
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
                            convertLocation(team.getLocation()));
    }

    private static org.amhzing.clusterview.domain.model.Location convertLocation(final Location location) {
        return org.amhzing.clusterview.domain.model.Location.create(location.getX(), location.getY());
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

    private static org.amhzing.clusterview.domain.model.Name convertName(final Name name) {
        final String firstName = name.getFirstName();
        final String middleName = name.getMiddleName();
        final String lastName = name.getLastName();
        final String suffix = name.getSuffix();

        return org.amhzing.clusterview.domain.model.Name.create(isBlank(firstName) ? null : FirstName.create(firstName),
                                                                isBlank(middleName) ? null : MiddleName.create(middleName),
                                                                isBlank(lastName) ? null : LastName.create(lastName),
                                                                isBlank(suffix) ? null : Suffix.create(suffix));
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
