package org.amhzing.clusterview.app.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.app.cache.CacheSpec.STATS_HISTORY_CACHE_NAME;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.statsHistoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultStatisticHistoryRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";
    private static final String NON_EXISTING_CLUSTER = "ClusterDoesNotExist";

    public static final String CACHE_TEST_VALUE = "value";
    public static final String CACHE_TEST_VALUE_2 = "value2";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    @Autowired
    private StatisticHistoryRepository statisticHistoryRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(statsHistoryJpaRepository.findByStatsHistoryPkClusterId(EXISTING_CLUSTER)).willReturn(ImmutableList.of(statsHistoryEntity()));

        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER);

        // First call
        final List<DatedActivityStatistic> firstCall = statisticHistoryRepository.history(clusterId);

        // Second call
        final List<DatedActivityStatistic> secondCall = statisticHistoryRepository.history(clusterId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(statsHistoryJpaRepository, times(1)).findByStatsHistoryPkClusterId(clusterId.getId());
    }

    @Test
    public void should_invoke_cache_when_empty() throws Exception {

        final Cluster.Id clusterId = Cluster.Id.create(NON_EXISTING_CLUSTER);

        // First call
        final List<DatedActivityStatistic> firstCall = statisticHistoryRepository.history(clusterId);

        // Second call
        final List<DatedActivityStatistic> secondCall = statisticHistoryRepository.history(clusterId);

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(statsHistoryJpaRepository, times(1)).findByStatsHistoryPkClusterId(clusterId.getId());
    }

    @Test
    public void should_evict_caches_when_saving() throws Exception {

        givenAPopulatedCache();

        whenSavingHistory();

        // Only the cluster for the saved history is cleared
        assertThat(cacheValue(STATS_HISTORY_CACHE_NAME, statsHistoryCacheKey())).isNull();
        assertThat(cacheValue(STATS_HISTORY_CACHE_NAME, statsHistoryCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);

    }

    private void givenAPopulatedCache() {
        given(statsHistoryJpaRepository.findByStatsHistoryPkClusterId(EXISTING_CLUSTER)).willReturn(ImmutableList.of(statsHistoryEntity()));

        cacheManager.getCache(STATS_HISTORY_CACHE_NAME).put(statsHistoryCacheKey(), CACHE_TEST_VALUE);
        cacheManager.getCache(STATS_HISTORY_CACHE_NAME).put(statsHistoryCacheKey2(), CACHE_TEST_VALUE_2);

        assertThat(cacheValue(STATS_HISTORY_CACHE_NAME, statsHistoryCacheKey())).isEqualTo(CACHE_TEST_VALUE);
        assertThat(cacheValue(STATS_HISTORY_CACHE_NAME, statsHistoryCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);
    }

    private void whenSavingHistory() {
        when(statsHistoryJpaRepository.save(any(StatsHistoryEntity.class))).thenReturn(statsHistoryEntity());
        statisticHistoryRepository.saveHistory(clusterId(), activityStatistic());
    }

    private String statsHistoryCacheKey() {
        return STATS_HISTORY_CACHE_NAME + '_' + clusterId();
    }

    private String statsHistoryCacheKey2() {
        final Cluster.Id clusterId = Cluster.Id.create("AnotherCluster");
        return STATS_HISTORY_CACHE_NAME + '_' + clusterId;
    }

    private Cluster.Id clusterId() {
        return Cluster.Id.create(EXISTING_CLUSTER);
    }

    private String cacheValue(final String name, final String key) {
        final org.springframework.cache.Cache.ValueWrapper valueWrapper = cacheManager.getCache(name).get(key);

        if (valueWrapper == null) {
            return null;
        }

        return (String) valueWrapper.get();
    }
}