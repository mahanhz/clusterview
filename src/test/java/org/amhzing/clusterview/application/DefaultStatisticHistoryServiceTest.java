package org.amhzing.clusterview.application;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticHistoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.helper.DomainModelHelper.clusterId;
import static org.amhzing.clusterview.helper.DomainModelHelper.datedActivityStatistic;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.statsHistoryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultStatisticHistoryServiceTest {

    @Mock
    private StatisticHistoryRepository statisticHistoryRepository;

    private DefaultStatisticHistoryService defaultStatisticHistoryService;

    @Before
    public void setUp() throws Exception {
        defaultStatisticHistoryService = new DefaultStatisticHistoryService(statisticHistoryRepository);
    }

    @Test
    public void should_get_history() throws Exception {

        given(statisticHistoryRepository.history(any())).willReturn(ImmutableList.of(datedActivityStatistic()));

        final List<DatedActivityStatistic> history = defaultStatisticHistoryService.history(clusterId());

        assertThat(history).isNotEmpty();

        final DatedActivityStatistic datedActivityStatistic = history.get(0);
        assertThat(datedActivityStatistic.getDate()).isEqualToIgnoringHours(datedActivityStatistic().getDate());
    }

    @Test
    public void should_save_history() throws Exception {

        given(statisticHistoryRepository.saveHistory(any(), any())).willReturn(statsHistoryEntity());

        final Cluster.Id clusterId = clusterId();
        final ActivityStatistic activityStatistic = activityStatistic();

        defaultStatisticHistoryService.saveHistory(clusterId, activityStatistic);

        verify(statisticHistoryRepository, times(1)).saveHistory(eq(clusterId), eq(activityStatistic));
    }
}