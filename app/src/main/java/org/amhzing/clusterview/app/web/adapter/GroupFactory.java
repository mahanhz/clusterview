package org.amhzing.clusterview.app.web.adapter;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.app.domain.model.*;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.web.model.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public final class GroupFactory {

    private GroupFactory() {
        // To prevent instantiation
    }

    public static Group convert(final GroupModel groupModel) {
        notNull(groupModel);

        final long id = id(groupModel.getObfuscatedId());

        return Group.create(Group.Id.create(id),
                            convertMembers(groupModel.getMembers()),
                            convertLocation(groupModel.getLocation()),
                            convertCoreActivities(groupModel.getCoreActivities()));
    }

    private static long id(final String obfuscatedId) {
        if (StringUtils.isNotBlank(obfuscatedId)){
            return Obfuscator.deobfuscate(obfuscatedId);
        }

        return 0;
    }

    private static Location convertLocation(final LocationModel location) {
        return ImmutableLocation.of(location.getCoordX(), location.getCoordY());
    }

    private static Set<CoreActivity> convertCoreActivities(final List<CoreActivityModel> coreActivityQuantities) {
        return coreActivityQuantities.stream()
                                     .map(GroupFactory::convertCoreActivity)
                                     .collect(Collectors.toSet());
    }

    private static CoreActivity convertCoreActivity(final CoreActivityModel coreActivityModel) {
        return CoreActivity.create(CoreActivity.Id.create(coreActivityModel.getId()),
                                   coreActivityModel.getName(),
                                   Quantity.create(coreActivityModel.getQuantity()),
                                   Quantity.create(coreActivityModel.getTotalParticipants()),
                                   Quantity.create(coreActivityModel.getCommunityOfInterest()));
    }

    private static Set<Member> convertMembers(final List<MemberModel> members) {
        return members.stream()
                      .map(GroupFactory::convertMember)
                      .collect(Collectors.toSet());
    }

    private static Member convertMember(final MemberModel member) {

        final long id = id(member.getObfuscatedId());

        return Member.create(Member.Id.create(id),
                             convertName(member.getName()),
                             convertCapabilities(member.getCapability()),
                             convertCommitments(member.getCommitment()));
    }

    private static Name convertName(final NameModel name) {
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
        // TODO - Passing Id as name since only id is later used to get the actual activity
        return Activity.create(Activity.Id.create(activity.getId()), activity.getId());
    }
}
