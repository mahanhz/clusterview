package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.web.model.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class GroupModelFactory {

    private GroupModelFactory() {
        // To prevent instantiation
    }

    public static Set<GroupModel> convertGroups(final Set<Group> groups) {
        return groups.stream()
                     .map(GroupModelFactory::convertGroup)
                     .collect(Collectors.toSet());
    }

    public static GroupModel convertGroup(final Group group) {
        return GroupModel.create(group.getId().getId(),
                                 convertMembers(group.getMembers()),
                                 convertLocation(group.getLocation()));
    }

    private static LocationModel convertLocation(final Location location) {
        return LocationModel.create(location.getCoordX(), location.getCoordY());
    }

    private static List<MemberModel> convertMembers(final Set<Member> members) {
        return members.stream()
                      .map(GroupModelFactory::convertMember)
                      .collect(Collectors.toList());
    }

    private static MemberModel convertMember(final Member member) {
        return MemberModel.create(member.getId().getId(),
                                  convertName(member.getName()),
                                  convertCapabilities(member.getCapability()),
                                  convertCommitments(member.getCommitment()));
    }

    private static NameModel convertName(final Name name) {

        final FirstName firstName = name.getFirstName();
        final MiddleName middleName = name.getMiddleName();
        final LastName lastName = name.getLastName();
        final Suffix suffix = name.getSuffix();

        return NameModel.create(firstName == null ? "" : firstName.getValue(),
                                middleName == null ? "" : middleName.getValue(),
                                lastName == null ? "" : lastName.getValue(),
                                suffix == null ? "" : suffix.getValue());
    }

    private static CapabilityModel convertCapabilities(final Capability capability) {
        return CapabilityModel.create(activities(capability.getActivities()));
    }

    private static CommitmentModel convertCommitments(final Commitment commitment) {
        return CommitmentModel.create(activities(commitment.getActivities()));
    }

    private static List<ActivityModel> activities(final Set<Activity> activities) {
        return activities.stream()
                         .map(GroupModelFactory::activity)
                         .collect(Collectors.toList());
    }

    private static ActivityModel activity(final Activity activity) {
        return ActivityModel.create(activity.getId().getId(), activity.getName());
    }
}
