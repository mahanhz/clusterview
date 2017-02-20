package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.backend.domain.model.Country;
import org.amhzing.clusterview.backend.domain.model.Region;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.backend.infra.repository.RegionStatisticRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.backend.helper.DomainModelHelper.activity;
import static org.amhzing.clusterview.backend.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.backend.helper.DomainModelHelper.quantity;
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

    private DefaultStatisticService defaultStatisticService;

    @Before
    public void setUp() throws Exception {
        defaultStatisticService = new DefaultStatisticService(countryStatisticRepository,
                                                              regionStatisticRepository,
                                                              clusterStatisticRepository);
    }

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


}