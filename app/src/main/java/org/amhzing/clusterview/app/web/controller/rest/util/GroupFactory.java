package org.amhzing.clusterview.app.web.controller.rest.util;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.api.*;
import org.amhzing.clusterview.app.api.statistic.CoreActivityDTO;
import org.amhzing.clusterview.app.domain.model.*;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.web.Obfuscator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public final class GroupFactory {

    private GroupFactory() {
        // To prevent instantiation
    }

    public static Group convert(final GroupDTO groupDto) {
        notNull(groupDto);

        final long id = id(groupDto.id);

        return Group.create(Group.Id.create(id),
                            convertMembers(groupDto.members),
                            convertLocation(groupDto.location),
                            convertCoreActivities(groupDto.coreActivities));
    }

    private static long id(final String obfuscatedId) {
        if (StringUtils.isNotBlank(obfuscatedId)){
            return Obfuscator.deobfuscate(obfuscatedId);
        }

        return 0;
    }

    private static Location convertLocation(final LocationDTO location) {
        return ImmutableLocation.of(location.x, location.y);
    }

    private static Set<CoreActivity> convertCoreActivities(final List<CoreActivityDTO> coreActivitiesDto) {
        return coreActivitiesDto.stream()
                                .map(GroupFactory::convertCoreActivity)
                                .collect(Collectors.toSet());
    }

    private static CoreActivity convertCoreActivity(final CoreActivityDTO coreActivity) {
        return CoreActivity.create(CoreActivity.Id.create(coreActivity.id),
                                   coreActivity.name,
                                   Quantity.create(coreActivity.quantity),
                                   Quantity.create(coreActivity.totalParticipants),
                                   Quantity.create(coreActivity.communityOfInterest));
    }

    private static Set<Member> convertMembers(final List<MemberDTO> members) {
        return members.stream()
                      .map(GroupFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static Member convertMember(final MemberDTO member) {

        return Member.create(Member.Id.create(0L),
                             convertName(member.name),
                             convertCapabilities(member.capabilities),
                             convertCommitments(member.commitments));
    }

    private static Name convertName(final NameDTO name) {
        final String firstName = name.firstName;
        final String middleName = StringUtils.defaultIfBlank(name.middleName, "");
        final String lastName = name.lastName;
        final String suffix = StringUtils.defaultIfBlank(name.suffix, "");

        return ImmutableName.builder()
                            .firstName(ImmutableFirstName.of(firstName))
                            .middleName(ImmutableMiddleName.of(middleName))
                            .lastName(ImmutableLastName.of(lastName))
                            .suffix(ImmutableSuffix.of(suffix))
                            .build();
    }

    private static Capability convertCapabilities(final List<ReferenceActivityDTO> capabilities) {
        final Set<Activity> activities = capabilities.stream()
                                                     .map(GroupFactory::activity)
                                                     .collect(Collectors.toSet());

        return Capability.create(activities);
    }

    private static Commitment convertCommitments(final List<ReferenceActivityDTO> commitments) {
        final Set<Activity> activities = commitments.stream()
                                                    .map(GroupFactory::activity)
                                                    .collect(Collectors.toSet());

        return Commitment.create(activities);
    }

    private static Activity activity(final ReferenceActivityDTO activity) {
        return Activity.create(Activity.Id.create(activity.id), activity.name);
    }
}
