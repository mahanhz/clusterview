package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.web.model.*;

import java.util.Set;
import java.util.stream.Collectors;

public final class AdapterFactory {

    private AdapterFactory() {
        // To prevent instantiation
    }

    public static Set<GroupModel> convertGroups(final Set<Group> groups) {
        return groups.stream()
                     .map(AdapterFactory::convertGroup)
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

    private static Set<MemberModel> convertMembers(final Set<Member> members) {
        return members.stream()
                      .map(AdapterFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static MemberModel convertMember(final Member member) {
        return MemberModel.create(member.getId().getId(),
                                  convertName(member.getName()),
                                  convertCapabilities(member.getCapability()),
                                  convertCommitments(member.getCommitment()));
    }

    private static NameModel convertName(final Name name) {
        return NameModel.create(name.getFirstName().getValue(),
                                name.getMiddleName().getValue(),
                                name.getLastName().getValue(),
                                name.getSuffix().getValue());
    }

    private static CapabilityModel convertCapabilities(final Capability capability) {
        return CapabilityModel.create(activities(capability.getActivities()));
    }

    private static CommitmentModel convertCommitments(final Commitment commitment) {
        return CommitmentModel.create(activities(commitment.getActivities()));
    }

    private static Set<ActivityModel> activities(final Set<Activity> activities) {
        return activities.stream()
                         .map(AdapterFactory::activity)
                         .collect(Collectors.toSet());
    }

    private static ActivityModel activity(final Activity activity) {
        return ActivityModel.create(activity.getId().getId(), activity.getName());
    }
}
