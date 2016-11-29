package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Quantity;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import java.util.Map;
import java.util.stream.Stream;

import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

public class ClusterStatisticRepository implements StatisticRepository<Cluster.Id> {

    private ClusterJpaRepository clusterJpaRepository;

    public ClusterStatisticRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    public ActivityStatistic statistics(final Cluster.Id id) {

        final ClusterEntity cluster = clusterJpaRepository.findOne(id.getId());

        final Stream<TeamEntity> teamEntityStream = teamEntities(cluster);

        final Stream<MemberEntity> memberEntityStream = memberEntities(teamEntityStream);

        final Stream<Activity> activityStream = activities(memberEntityStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        return ActivityStatistic.create(activityQuantityMap);
    }
}
