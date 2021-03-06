package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.appui.web.model.*;
import org.amhzing.clusterview.core.domain.*;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupModelFactory {

    private GroupModelFactory() {
        // To prevent instantiation
    }

    public static Set<GroupModel> convertGroups(final Set<Group> groups) {
        noNullElements(groups);

        return groups.stream()
                     .map(GroupModelFactory::convertGroup)
                     .collect(Collectors.toSet());
    }

    public static GroupModel convertGroup(final Group group) {
        notNull(group);

        final String obfuscatedId = Obfuscator.obfuscate(group.getId().getId());

        return GroupModel.create(obfuscatedId,
                          convertMembers(group.getMembers()),
                          convertLocation(group.getLocation()),
                          coreActivities(group.getCoreActivities()));
    }

    private static LocationModel convertLocation(final Location location) {
        return LocationModel.create(location.coordX(), location.coordY());
    }

    private static List<CoreActivityModel> coreActivities(final Set<CoreActivity> coreActivities) {
        return coreActivities.stream()
                             .map(coreActivity -> convertCoreActivity(coreActivity))
                             .sorted(Comparator.comparing(CoreActivityModel::getName))
                             .collect(Collectors.toList());
    }

    private static CoreActivityModel convertCoreActivity(final CoreActivity coreActivity) {
        return CoreActivityModel.create(coreActivity.getId().getId(),
                                        coreActivity.getName(),
                                        coreActivity.getQuantity().getValue(),
                                        coreActivity.getTotalParticipants().getValue(),
                                        coreActivity.getCommunityOfInterest().getValue());
    }

    private static List<MemberModel> convertMembers(final Set<Member> members) {
        return members.stream()
                      .map(GroupModelFactory::convertMember)
                      .sorted(Comparator.comparing(a -> a.getName().getLastName()))
                      .collect(Collectors.toList());
    }

    private static MemberModel convertMember(final Member member) {

        final String obfuscatedId = Obfuscator.obfuscate(member.getId().getId());

        return MemberModel.create(obfuscatedId,
                                  convertName(member.getName()),
                                  convertCapabilities(member.getCapability()),
                                  convertCommitments(member.getCommitment()));
    }

    private static NameModel convertName(final Name name) {

        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return NameModel.create(firstName == null ? "" : firstName.value(),
                                middleName == null ? "" : middleName.value(),
                                lastName == null ? "" : lastName.value(),
                                suffix == null ? "" : suffix.value());
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
                         .sorted(Comparator.comparing(ActivityModel::getName))
                         .collect(Collectors.toList());
    }

    private static ActivityModel activity(final Activity activity) {
        return ActivityModel.create(activity.getId().getId(), activity.getName());
    }
}
