package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.Quantity;
import org.amhzing.clusterview.infra.jpa.mapping.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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

    private static Activity activity(final ActivityEntity activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }
}
