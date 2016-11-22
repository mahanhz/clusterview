package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.mapping.Location;
import org.amhzing.clusterview.infra.jpa.mapping.Name;

import java.util.Set;
import java.util.stream.Collectors;

public final class RepositoryFactory {

    private RepositoryFactory() {
        // To prevent instantiation
    }

    public static Set<Group> convertTeams(final Set<TeamEntity> teams) {
        return teams.stream()
                    .map(RepositoryFactory::convertGroup)
                    .collect(Collectors.toSet());
    }

    public static Group convertGroup(final TeamEntity team) {
        return Group.create(Group.Id.create(team.getId()),
                            convertMembers(team.getMembers()),
                            convertLocation(team.getLocation()));
    }

    private static org.amhzing.clusterview.domain.model.Location convertLocation(final Location location) {
        return org.amhzing.clusterview.domain.model.Location.create(location.getX(), location.getY());
    }

    private static Set<Member> convertMembers(final Set<MemberEntity> members) {
        return members.stream()
                      .map(RepositoryFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static Member convertMember(final MemberEntity member) {
        return Member.create(Member.Id.create(member.getId()),
                             convertName(member.getName()),
                             convertCapabilities(member.getCapabilities()),
                             convertCommitments(member.getCommitments()));
    }

    private static org.amhzing.clusterview.domain.model.Name convertName(final Name name) {
        return org.amhzing.clusterview.domain.model.Name.create(FirstName.create(name.getFirstName()),
                                                                MiddleName.create(name.getMiddleName()),
                                                                LastName.create(name.getLastName()),
                                                                Suffix.create(name.getSuffix()));
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
