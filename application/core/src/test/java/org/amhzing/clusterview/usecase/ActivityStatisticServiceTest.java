package org.amhzing.clusterview.usecase;


import org.amhzing.clusterview.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ActivityStatisticServiceTest {

    @Mock
    private StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository;
    @Mock
    private StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository;
    @Mock
    private StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository;

    private ActivityStatisticService activityStatisticService;

    @Before
    public void setUp() throws Exception {
        activityStatisticService = new ActivityStatisticService(countryStatisticRepository,
                                                                regionStatisticRepository,
                                                                clusterStatisticRepository);
    }

    @Test
    public void should_get_country_stats() throws Exception {

        given(countryStatisticRepository.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = activityStatisticService.statistics(Country.Id.create("se"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }

    @Test
    public void should_get_region_stats() throws Exception {

        given(regionStatisticRepository.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = activityStatisticService.statistics(Region.Id.create("central"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }

    @Test
    public void should_get_cluster_stats() throws Exception {

        given(clusterStatisticRepository.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ActivityStatistic stats = activityStatisticService.statistics(Cluster.Id.create("stockholm"));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity())).isEqualTo(quantity());
    }


}