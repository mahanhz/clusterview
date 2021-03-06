package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.repository.RegionJpaRepository;
import org.amhzing.clusterview.data.jpa.entity.RegionEntity;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.core.domain.Member;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.Quantity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.amhzing.clusterview.data.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.data.cache.CacheSpec.STATS_CACHE_NAME;
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

        final Stream<ClusterEntity> clusterEntityStream = StatisticFactory.clusterEntities(region);

        final Set<Group> groups = StatisticFactory.groups(clusterEntityStream, groupRepository);

        final Stream<Member> memberStream = StatisticFactory.members(groups.stream());

        final Stream<Activity> activityStream = StatisticFactory.activities(memberStream);

        final Map<Activity, Quantity> activityQuantityMap = StatisticFactory.activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = StatisticFactory.coreActivities(groups);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}
