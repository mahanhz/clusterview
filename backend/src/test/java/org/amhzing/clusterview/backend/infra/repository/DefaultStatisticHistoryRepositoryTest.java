package org.amhzing.clusterview.backend.infra.repository;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.backend.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.backend.helper.DomainModelHelper;
import org.amhzing.clusterview.backend.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.backend.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.backend.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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

        given(statsHistoryJpaRepository.findByStatsHistoryPkClusterId(any(String.class))).willReturn(ImmutableList.of(JpaRepositoryHelper.statsHistoryEntity()));

        final List<DatedActivityStatistic> history = defaultStatisticHistoryRepository.history(DomainModelHelper.cluster().getId());

        assertThat(history).hasSize(1);
    }

    @Test
    public void should_save_history() throws Exception {

        given(statsHistoryJpaRepository.save(any(StatsHistoryEntity.class))).willReturn(JpaRepositoryHelper.statsHistoryEntity());
        final DatedActivityStatistic datedActivityStatistic = defaultStatisticHistoryRepository.saveHistory(DomainModelHelper.cluster().getId(),
                                                                                                            DomainModelHelper.activityStatistic());

        assertThat(datedActivityStatistic).isNotNull();
    }
}