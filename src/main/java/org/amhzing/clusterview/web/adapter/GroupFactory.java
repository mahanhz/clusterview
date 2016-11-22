package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.web.model.*;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public final class GroupFactory {

    private GroupFactory() {
        // To prevent instantiation
    }

    public static Group convert(final GroupModel groupModel) {
        notNull(groupModel);

        return Group.create(convertMembers(groupModel.getMembers()),
                            convertLocation(groupModel.getLocation()));
    }

    private static Location convertLocation(final LocationModel location) {
        return org.amhzing.clusterview.domain.model.Location.create(location.getCoordX(), location.getCoordY());
    }

    private static Set<Member> convertMembers(final Set<MemberModel> members) {
        return members.stream()
                      .map(GroupFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static Member convertMember(final MemberModel member) {
        return Member.create(convertName(member.getName()),
                             convertCapabilities(member.getCapability()),
                             convertCommitments(member.getCommitment()));
    }

    private static Name convertName(final NameModel name) {
        return org.amhzing.clusterview.domain.model.Name.create(FirstName.create(name.getFirstName()),
                                                                MiddleName.create(name.getMiddleName()),
                                                                LastName.create(name.getLastName()),
                                                                Suffix.create(name.getSuffix()));
    }

    private static Capability convertCapabilities(final CapabilityModel capability) {
        final Set<Activity> activities = capability.getActivities()
                                                .stream()
                                                .map(GroupFactory::activity)
                                                .collect(Collectors.toSet());

        return Capability.create(activities);
    }

    private static Commitment convertCommitments(final CommitmentModel commitment) {
        final Set<Activity> activities = commitment.getActivities()
                                                   .stream()
                                                   .map(GroupFactory::activity)
                                                   .collect(Collectors.toSet());

        return Commitment.create(activities);
    }

    private static Activity activity(final ActivityModel activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }
}
