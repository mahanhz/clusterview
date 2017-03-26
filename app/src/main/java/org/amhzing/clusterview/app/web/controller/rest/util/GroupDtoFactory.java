package org.amhzing.clusterview.app.web.controller.rest.util;

import org.amhzing.clusterview.app.api.*;
import org.amhzing.clusterview.app.api.statistic.CoreActivityDTO;
import org.amhzing.clusterview.app.domain.model.*;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.web.adapter.Obfuscator;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupDtoFactory {

    private GroupDtoFactory() {
        // To prevent instantiation
    }

    public static GroupsDTO convertGroups(final Set<Group> groups) {
        noNullElements(groups);

        final List<GroupDTO> groupsDto = groups.stream()
                                             .map(GroupDtoFactory::convertGroup)
                                             .collect(Collectors.toList());

        return new GroupsDTO(groupsDto);
    }

    public static GroupDTO convertGroup(final Group group) {
        notNull(group);

        final String obfuscatedId = Obfuscator.obfuscate(group.getId().getId());

        return new GroupDTO(obfuscatedId,
                          convertMembers(group.getMembers()),
                          convertLocation(group.getLocation()),
                          coreActivities(group.getCoreActivities()));
    }

    private static LocationDTO convertLocation(final Location location) {
        return new LocationDTO(location.coordX(), location.coordY());
    }

    private static List<CoreActivityDTO> coreActivities(final Set<CoreActivity> coreActivities) {
        return coreActivities.stream()
                             .map(coreActivity -> convertCoreActivity(coreActivity))
                             .sorted(Comparator.comparing(a -> a.name))
                             .collect(Collectors.toList());
    }

    private static CoreActivityDTO convertCoreActivity(final CoreActivity coreActivity) {
        return new CoreActivityDTO(coreActivity.getId().getId(),
                                   coreActivity.getName(),
                                   (int) coreActivity.getQuantity().getValue(),
                                   (int) coreActivity.getTotalParticipants().getValue(),
                                   (int) coreActivity.getCommunityOfInterest().getValue());
    }

    private static List<MemberDTO> convertMembers(final Set<Member> members) {
        return members.stream()
                      .map(GroupDtoFactory::convertMember)
                      .sorted(Comparator.comparing(a -> a.name.lastName))
                      .collect(Collectors.toList());
    }

    private static MemberDTO convertMember(final Member member) {

        return new MemberDTO(convertName(member.getName()),
                             convertCapabilities(member.getCapability()),
                             convertCommitments(member.getCommitment()));
    }

    private static NameDTO convertName(final Name name) {

        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return new NameDTO(firstName == null ? "" : firstName.value(),
                           middleName == null ? "" : middleName.value(),
                           lastName == null ? "" : lastName.value(),
                           suffix == null ? "" : suffix.value());
    }

    private static List<ReferenceActivityDTO> convertCapabilities(final Capability capability) {
        return activities(capability.getActivities());
    }

    private static List<ReferenceActivityDTO> convertCommitments(final Commitment commitment) {
        return activities(commitment.getActivities());
    }

    private static List<ReferenceActivityDTO> activities(final Set<Activity> activities) {
        return activities.stream()
                         .map(GroupDtoFactory::activity)
                         .sorted(Comparator.comparing(a -> a.name))
                         .collect(Collectors.toList());
    }

    private static ReferenceActivityDTO activity(final Activity activity) {
        return new ReferenceActivityDTO(activity.getId().getId(), activity.getName());
    }
}
