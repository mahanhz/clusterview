package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.core.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.core.domain.Member;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.Quantity;
import org.amhzing.clusterview.data.cache.CacheSpec;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.entity.CountryEntity;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CacheSpec.STATS_CACHE_NAME)
public class CountryStatisticRepository implements StatisticRepository<Country.Id, ActivityStatistic> {

    private CountryJpaRepository countryJpaRepository;
    private GroupRepository groupRepository;

    public CountryStatisticRepository(final CountryJpaRepository countryJpaRepository,
                                      final GroupRepository groupRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
        this.groupRepository = notNull(groupRepository);
    }

    @Override
    @Cacheable(key= CacheSpec.DEFAULT_CACHE_KEY, unless = "#result == null")
    public ActivityStatistic statistics(final Country.Id id) {
        notNull(id);

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        if (country == null) {
            return ActivityStatistic.empty();
        }

        final Stream<ClusterEntity> clusterEntityStream = StatisticFactory.clusterEntities(country);

        final Set<Group> groups = StatisticFactory.groups(clusterEntityStream, groupRepository);

        final Stream<Member> memberStream = StatisticFactory.members(groups.stream());

        final Stream<Activity> activityStream = StatisticFactory.activities(memberStream);

        final Map<Activity, Quantity> activityQuantityMap = StatisticFactory.activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = StatisticFactory.coreActivities(groups);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}
