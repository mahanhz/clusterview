package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.app.infra.repository.RegionStatisticRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.activity;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.quantity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ActivityStatisticServiceTest {

    @Mock
    private CountryStatisticRepository countryStatisticRepository;
    @Mock
    private RegionStatisticRepository regionStatisticRepository;
    @Mock
    private ClusterStatisticRepository clusterStatisticRepository;

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