package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Activity;
import org.amhzing.clusterview.app.domain.model.Group;
import org.amhzing.clusterview.app.domain.model.Member;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.domain.repository.GroupRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.amhzing.clusterview.app.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_CACHE_NAME;
import static org.amhzing.clusterview.app.infra.repository.StatisticFactory.*;
import static org.amhzing.clusterview.app.infra.repository.StatisticFactory.activities;
import static org.amhzing.clusterview.app.infra.repository.StatisticFactory.activityQuantities;
import static org.amhzing.clusterview.app.infra.repository.StatisticFactory.members;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_CACHE_NAME)
public class ClusterStatisticRepository implements StatisticRepository<Cluster.Id, ActivityStatistic> {

    private ClusterJpaRepository clusterJpaRepository;
    private GroupRepository groupRepository;

    public ClusterStatisticRepository(final ClusterJpaRepository clusterJpaRepository,
                                      final GroupRepository groupRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
        this.groupRepository = notNull(groupRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public ActivityStatistic statistics(final Cluster.Id id) {

        final ClusterEntity cluster = clusterJpaRepository.findOne(id.getId());

        if (cluster == null) {
            return ActivityStatistic.empty();
        }

        final Set<Group> groups = groupRepository.groups(Cluster.Id.create(cluster.getId()));

        final Stream<Member> memberStream = members(groups.stream());

        final Stream<Activity> activityStream = activities(memberStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = coreActivities(groups);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}