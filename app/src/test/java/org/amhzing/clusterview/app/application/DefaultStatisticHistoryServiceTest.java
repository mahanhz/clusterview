package org.amhzing.clusterview.app.application;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.app.helper.DomainModelHelper;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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

        given(statisticHistoryRepository.history(any())).willReturn(ImmutableList.of(DomainModelHelper.datedActivityStatistic()));

        final List<DatedActivityStatistic> history = defaultStatisticHistoryService.history(DomainModelHelper.clusterId());

        assertThat(history).isNotEmpty();

        final DatedActivityStatistic datedActivityStatistic = history.get(0);
        assertThat(datedActivityStatistic.getDate()).isEqualToIgnoringHours(DomainModelHelper.datedActivityStatistic().getDate());
    }

    @Test
    public void should_save_history() throws Exception {

        given(statisticHistoryRepository.saveHistory(any(), any())).willReturn(DomainModelHelper.datedActivityStatistic());

        final Cluster.Id clusterId = DomainModelHelper.clusterId();
        final ActivityStatistic activityStatistic = DomainModelHelper.activityStatistic();

        defaultStatisticHistoryService.saveHistory(clusterId, activityStatistic);

        verify(statisticHistoryRepository, times(1)).saveHistory(eq(clusterId), eq(activityStatistic));
    }
}