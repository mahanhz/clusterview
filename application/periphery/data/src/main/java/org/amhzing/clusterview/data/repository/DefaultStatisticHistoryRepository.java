package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.data.jpa.entity.stats.ActivityStats;
import org.amhzing.clusterview.data.jpa.entity.stats.StatsHistoryEntity;
import org.amhzing.clusterview.data.jpa.entity.stats.StatsHistoryPk;
import org.amhzing.clusterview.data.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.data.repository.visitor.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.data.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.data.cache.CacheSpec.STATS_HISTORY_CACHE_NAME;
import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.*;
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
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    @CacheEvict(cacheNames = STATS_HISTORY_CACHE_NAME, key = DEFAULT_CACHE_KEY)
    public DatedActivityStatistic saveHistory(final Cluster.Id clusterId, final ActivityStatistic activityStatistic) {
        notNull(clusterId);
        notNull(activityStatistic);

        final StatsHistoryPk statsHistoryPk = StatsHistoryPk.create(clusterId.getId(), thisMonth());

        final Optional<CoreActivity> cc = activityStatistic.accept(new CoreActivityStatsHistoryCCVisitor());
        final Optional<CoreActivity> dm = activityStatistic.accept(new CoreActivityStatsHistoryDMVisitor());
        final Optional<CoreActivity> jyg = activityStatistic.accept(new CoreActivityStatsHistoryJYGVisitor());
        final Optional<CoreActivity> sc = activityStatistic.accept(new CoreActivityStatsHistorySCVisitor());

        final ActivityStats activityStats = ActivityStats.create(activityStatistic.accept(new ActivityStatsHistoryCCVisitor()),
                                                                 activityStatistic.accept(new ActivityStatsHistoryDMVisitor()),
                                                                 activityStatistic.accept(new ActivityStatsHistoryFiresideVisitor()),
                                                                 activityStatistic.accept(new ActivityStatsHistoryJYGVisitor()),
                                                                 activityStatistic.accept(new ActivityStatsHistorySCVisitor()));

        final StatsHistoryEntity statsHistoryEntity = StatsHistoryEntity.create(statsHistoryPk,
                                                                                coreActivityStats(cc),
                                                                                coreActivityStats(dm),
                                                                                coreActivityStats(jyg),
                                                                                coreActivityStats(sc),
                                                                                activityStats);

        return datedActivityStatistic(statsHistoryJpaRepository.save(statsHistoryEntity));
    }
}
