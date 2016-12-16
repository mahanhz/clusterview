package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.infra.jpa.mapping.stats.CoreActivityStats;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryPk;
import org.amhzing.clusterview.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.cache.CacheSpec.*;
import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = STATS_HISTORY_CACHE_NAME)
public class DefaultStatisticHistoryRepository implements StatisticHistoryRepository {

    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    public DefaultStatisticHistoryRepository(final StatsHistoryJpaRepository statsHistoryJpaRepository) {
        this.statsHistoryJpaRepository = notNull(statsHistoryJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null")
    public List<DatedActivityStatistic> history(final Cluster.Id clusterId) {
        notNull(clusterId);

        final List<StatsHistoryEntity> statsHistory = statsHistoryJpaRepository.findByStatsHistoryPkClusterId(clusterId.getId());

        if (statsHistory == null) {
            return emptyList();
        }

        return statsHistory.stream()
                           .map(StatisticHistoryFactory::datedActivityStatistic)
                           .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = STATS_HISTORY_CACHE_NAME, key = DEFAULT_CACHE_KEY)
    public StatsHistoryEntity saveHistory(final Cluster.Id clusterId, final ActivityStatistic activityStatistic) {
        notNull(clusterId);
        notNull(activityStatistic);

        final StatsHistoryPk statsHistoryPk = StatsHistoryPk.create(clusterId.getId(), thisMonth());

        final CoreActivityStats cc = new CoreActivityStats();
        final CoreActivityStats dm = new CoreActivityStats();
        final CoreActivityStats jyg = new CoreActivityStats();
        final CoreActivityStats sc = new CoreActivityStats();

        populateCoreActivitiesStats(activityStatistic, cc, dm, jyg, sc);

        final StatsHistoryEntity statsHistoryEntity = StatsHistoryEntity.create(statsHistoryPk,
                                                                                cc, dm, jyg, sc,
                                                                                activityStats(activityStatistic.getActivityQuantity()));

        return statsHistoryJpaRepository.save(statsHistoryEntity);
    }
}
