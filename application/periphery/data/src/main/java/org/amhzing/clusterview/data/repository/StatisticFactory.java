package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.entity.CountryEntity;
import org.amhzing.clusterview.data.jpa.entity.RegionEntity;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.core.domain.Member;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.Quantity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.Validate.notNull;

public final class StatisticFactory {

    private StatisticFactory() {
        // To prevent instantiation
    }

    public static Stream<ClusterEntity> clusterEntities(final CountryEntity country) {
        notNull(country);

        return country.getRegions()
                      .stream()
                      .flatMap(region -> region.getClusters().stream());
    }

    public static Stream<ClusterEntity> clusterEntities(final RegionEntity region) {
        notNull(region);

        return region.getClusters().stream();
    }

    public static Set<Group> groups(final Stream<ClusterEntity> clusterEntityStream,
                                    final GroupRepository groupRepository) {
        notNull(clusterEntityStream);
        notNull(groupRepository);

        return clusterEntityStream.flatMap(clusterEntity -> groupRepository.groups(Cluster.Id.create(clusterEntity.getId())).stream())
                                  .collect(Collectors.toSet());
    }

    public static Stream<Member> members(final Stream<Group> groupStream) {
        notNull(groupStream);

        return groupStream.flatMap(group -> group.getMembers().stream());
    }

    public static Stream<Activity> activities(final Stream<Member> memberStream) {
        notNull(memberStream);

        return memberStream.flatMap(member -> member.getCommitment().getActivities().stream());
    }

    public static Map<Activity, Quantity> activityQuantities(final Stream<Activity> activityStream) {
        notNull(activityStream);

        return activityStream.collect(groupingBy(Function.identity(),
                                                 collectingAndThen(counting(), Quantity::create)));
    }

    public static Set<CoreActivity> coreActivities(final Set<Group> groups) {
        notNull(groups);

        final Map<CoreActivity, List<CoreActivity>> grouped = groups.stream()
                                                                    .flatMap(group -> group.getCoreActivities().stream())
                                                                    .collect(groupingBy(coreActivity -> coreActivity));

        return grouped.entrySet()
                      .stream()
                      .map(a -> reduce(a))
                      .collect(Collectors.toSet());
    }

    private static CoreActivity reduce(final Map.Entry<CoreActivity, List<CoreActivity>> entry) {
        final CoreActivity key = entry.getKey();
        return entry.getValue().stream()
                                    .reduce((a, b) -> sumCoreActivities(a, b))
                                    .orElseGet(() -> key);

    }

    private static CoreActivity sumCoreActivities(final CoreActivity a, final CoreActivity b) {
        return CoreActivity.create(a.getId(),
                                   a.getName(),
                                   Quantity.create(a.getQuantity().getValue() + b.getQuantity().getValue()),
                                   Quantity.create(a.getTotalParticipants().getValue() + b.getTotalParticipants().getValue()),
                                   Quantity.create(a.getCommunityOfInterest().getValue() + b.getCommunityOfInterest().getValue()));
    }
}
