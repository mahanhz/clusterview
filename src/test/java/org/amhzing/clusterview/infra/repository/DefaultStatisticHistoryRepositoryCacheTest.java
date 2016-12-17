package org.amhzing.clusterview.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.configuration.CacheInvalidateRule;
import org.amhzing.clusterview.configuration.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.statsHistoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultStatisticHistoryRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";
    private static final String NON_EXISTING_CLUSTER = "ClusterDoesNotExist";

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
}