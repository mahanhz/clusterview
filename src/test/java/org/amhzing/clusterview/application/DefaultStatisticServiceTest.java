package org.amhzing.clusterview.application;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.activity;
import static org.amhzing.clusterview.helper.DomainModelHelper.coreActivity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultStatisticServiceTest {

    @Mock
    private CountryStatisticRepository countryStatisticRepository;
    @Mock
    private RegionStatisticRepository regionStatisticRepository;
    @Mock
    private ClusterStatisticRepository clusterStatisticRepository;

    @InjectMocks
    private DefaultStatisticService defaultStatisticService;

    @Test
    public void should_get_country_stats() throws Exception {

        given(countryStatisticRepository.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = defaultStatisticService.statistics(Country.Id.create("se"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }

    @Test
    public void should_get_region_stats() throws Exception {

        given(regionStatisticRepository.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = defaultStatisticService.statistics(Region.Id.create("central"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }

    @Test
    public void should_get_cluster_stats() throws Exception {

        given(clusterStatisticRepository.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = defaultStatisticService.statistics(Cluster.Id.create("stockholm"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }

    private ActivityStatistic activityStatistic() {
        return ActivityStatistic.create(ImmutableMap.of(activity(), quantity()), ImmutableSet.of(coreActivity()));
    }

    private Quantity quantity() {
        return Quantity.create(10);
    }
}