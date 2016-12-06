package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.amhzing.clusterview.cache.CacheSpec.STATS_CACHE_NAME;
import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_CACHE_NAME)
public class CountryStatisticRepository implements StatisticRepository<Country.Id, ActivityStatistic> {

    private CountryJpaRepository countryJpaRepository;

    public CountryStatisticRepository(final CountryJpaRepository countryJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
    }

    @Override
    @Cacheable(unless = "#result == null")
    public ActivityStatistic statistics(final Country.Id id) {

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        if (country == null) {
            return new ActivityStatistic();
        }

        final Stream<ClusterEntity> clusterEntityStream = clusterEntities(country);

        final Set<TeamEntity> teamEntitySet = teamEntities(clusterEntityStream).collect(Collectors.toSet());

        final Stream<MemberEntity> memberEntityStream = memberEntities(teamEntitySet.stream());

        final Stream<Activity> activityStream = activities(memberEntityStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        final Set<CoreActivity> coreActivities = coreActivities(teamEntitySet);

        return ActivityStatistic.create(activityQuantityMap, coreActivities);
    }
}
