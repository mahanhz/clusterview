package org.amhzing.clusterview.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.statsHistoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultStatisticHistoryRepositoryTest {

    @Mock
    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    private DefaultStatisticHistoryRepository defaultStatisticHistoryRepository;

    @Before
    public void setUp() throws Exception {
        defaultStatisticHistoryRepository = new DefaultStatisticHistoryRepository(statsHistoryJpaRepository);
    }

    @Test
    public void should_get_history() throws Exception {

        given(statsHistoryJpaRepository.findByStatsHistoryPkClusterId(any(String.class))).willReturn(ImmutableList.of(statsHistoryEntity()));

        final List<DatedActivityStatistic> history = defaultStatisticHistoryRepository.history(cluster().getId());

        assertThat(history).hasSize(1);
    }

    @Test
    public void should_save_history() throws Exception {

        given(statsHistoryJpaRepository.save(any(StatsHistoryEntity.class))).willReturn(statsHistoryEntity());
        final DatedActivityStatistic datedActivityStatistic = defaultStatisticHistoryRepository.saveHistory(cluster().getId(),
                                                                                                            activityStatistic());

        assertThat(datedActivityStatistic).isNotNull();
    }
}