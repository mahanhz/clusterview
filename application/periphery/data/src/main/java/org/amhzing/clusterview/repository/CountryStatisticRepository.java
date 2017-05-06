package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.cache.CacheSpec;
import org.amhzing.clusterview.domain.Activity;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Group;
import org.amhzing.clusterview.domain.Member;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.CoreActivity;
import org.amhzing.clusterview.domain.statistic.Quantity;
import org.amhzing.clusterview.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.jpa.entity.CountryEntity;
import org.amhzing.clusterview.jpa.repository.CountryJpaRepository;
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
