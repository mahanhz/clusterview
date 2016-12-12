package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.model.Member;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.RegionEntity;
import org.amhzing.clusterview.infra.jpa.repository.RegionJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.amhzing.clusterview.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.cache.CacheSpec.STATS_CACHE_NAME;
import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_CACHE_NAME)
public class RegionStatisticRepository implements StatisticRepository<Region.Id, ActivityStatistic> {

    private RegionJpaRepository regionJpaRepository;
    private GroupRepository groupRepository;

    public RegionStatisticRepository(final RegionJpaRepository regionJpaRepository,
                                     final GroupRepository groupRepository) {
        this.regionJpaRepository = notNull(regionJpaRepository);
        this.groupRepository = notNull(groupRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public ActivityStatistic statistics(final Region.Id id) {

        final RegionEntity region = regionJpaRepository.findOne(id.getId());

        if (region == null) {
            return ActivityStatistic.empty();
        }

        final Stream<ClusterEntity> clusterEntityStream = clusterEntities(region);

        final Set<Group> groups = groups(clusterEntityStream, groupRepository);

        final Stream<Member> memberStream = members(groups.stream());

        final Stream<Activity> activityStream = activities(memberStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = coreActivities(groups);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}
