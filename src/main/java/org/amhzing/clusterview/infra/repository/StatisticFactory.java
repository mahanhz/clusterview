package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.infra.jpa.mapping.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public final class StatisticFactory {

    private StatisticFactory() {
        // To prevent instantiation
    }

    public static Stream<ClusterEntity> clusterEntities(final CountryEntity country) {
        return country.getRegions()
                      .stream()
                      .flatMap(region -> region.getClusters().stream());
    }

    public static Stream<ClusterEntity> clusterEntities(final RegionEntity region) {
        return region.getClusters().stream();
    }

    public static Stream<TeamEntity> teamEntities(final Stream<ClusterEntity> clusterEntityStream) {
        return clusterEntityStream.flatMap(cluster -> cluster.getTeams().stream());
    }

    public static Stream<TeamEntity> teamEntities(final ClusterEntity cluster) {
        return cluster.getTeams().stream();
    }

    public static Stream<MemberEntity> memberEntities(final Stream<TeamEntity> teamEntityStream) {
        return teamEntityStream.flatMap(team -> team.getMembers().stream());
    }

    public static Stream<Activity> activities(final Stream<MemberEntity> memberEntityStream) {
        return memberEntityStream.flatMap(member -> member.getCommitments().stream())
                                 .map(commitment -> activity(commitment.getActivity()));
    }

    public static Map<Activity, Quantity> activityQuantities(final Stream<Activity> activityStream) {
        return activityStream.collect(groupingBy(Function.identity(),
                                                 collectingAndThen(counting(), Quantity::create)));
    }

    public static Set<CoreActivity> coreActivities(final Set<TeamEntity> teamEntityStream) {
        final Map<CoreActivityEntity, List<ParticipantQuantity>> collect =
                teamEntityStream.stream().flatMap(team -> team.getCoreActivities().entrySet().stream())
                                .collect(groupingBy(Map.Entry::getKey,
                                                    Collectors.mapping(a -> ParticipantQuantity.create(a.getValue().getTotal(),
                                                                                                       a.getValue().getCommunityOfInterest()),
                                                                       Collectors.toList())));

        return collect.entrySet()
                      .stream()
                      .map(StatisticFactory::convertCoreActivity)
                      .collect(Collectors.toSet());
    }

    private static Activity activity(final ActivityEntity activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }

    private static CoreActivity convertCoreActivity(final Map.Entry<CoreActivityEntity, List<ParticipantQuantity>> entry) {
        final CoreActivityEntity activity = entry.getKey();
        final ParticipantQuantity quantity = convertParticipantQuantities(entry.getValue());

        return CoreActivity.create(CoreActivity.Id.create(activity.getId()),
                                   activity.getName(),
                                   Quantity.create(quantity.getTotal()),
                                   Quantity.create(quantity.getCommunityOfInterest()));
    }

    private static ParticipantQuantity convertParticipantQuantities(final List<ParticipantQuantity> participantQuantities) {
        return participantQuantities.stream()
                                    .reduce((a, b) -> ParticipantQuantity.create(a.getTotal() + b.getTotal(),
                                                                                 a.getCommunityOfInterest() + b.getCommunityOfInterest()))
                                    .orElseGet(() -> ParticipantQuantity.create(0L, 0L));
    }
}
