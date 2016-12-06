package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.amhzing.clusterview.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.cache.CacheSpec.STATS_CACHE_NAME;
import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_CACHE_NAME)
public class ClusterStatisticRepository implements StatisticRepository<Cluster.Id, ActivityStatistic> {

    private ClusterJpaRepository clusterJpaRepository;

    public ClusterStatisticRepository(final ClusterJpaRepository clusterJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public ActivityStatistic statistics(final Cluster.Id id) {

        final ClusterEntity cluster = clusterJpaRepository.findOne(id.getId());

        if (cluster == null) {
            return new ActivityStatistic();
        }

        final Set<TeamEntity> teamEntitySet = teamEntities(cluster).collect(Collectors.toSet());

        final Stream<MemberEntity> memberEntityStream = memberEntities(teamEntitySet.stream());

        final Stream<Activity> activityStream = activities(memberEntityStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = coreActivities(teamEntitySet);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}
