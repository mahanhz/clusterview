package org.amhzing.clusterview.web.adapter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.application.ActivityService;
import org.amhzing.clusterview.application.StatisticService;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class StatisticAdapterTest {

    @Mock
    private StatisticService statisticService;
    @Mock
    private ActivityService activityService;

    @InjectMocks
    private StatisticAdapter statisticAdapter;

    @Test
    public void should_get_country_stats() throws Exception {

        given(statisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.countryStats(country().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(anotherActivity().getName())).isEqualTo(quantity().getValue());
    }

    @Test
    public void should_get_region_stats() throws Exception {

        given(statisticService.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.regionStats(region().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(anotherActivity().getName())).isEqualTo(quantity().getValue());
    }

    @Test
    public void should_get_cluster_stats() throws Exception {

        given(statisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.clusterStats(cluster().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(anotherActivity().getName())).isEqualTo(quantity().getValue());
    }

    private ActivityStatistic activityStatistic() {
        return ActivityStatistic.create(ImmutableMap.of(anotherActivity(), quantity()), ImmutableSet.of(coreActivity()));
    }

    private Quantity quantity() {
        return Quantity.create(10);
    }
}