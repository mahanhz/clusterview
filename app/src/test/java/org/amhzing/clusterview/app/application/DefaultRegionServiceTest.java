package org.amhzing.clusterview.app.application;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.repository.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    private DefaultRegionService defaultRegionService;

    @Before
    public void setUp() throws Exception {
        defaultRegionService = new DefaultRegionService(regionRepository);
    }

    @Test
    public void should_get_regions() throws Exception {

        given(regionRepository.regions(any())).willReturn(ImmutableList.of(regionId()));

        final List<Region.Id> regions = defaultRegionService.regions(country().getId());

        assertThat(regions).isNotEmpty();

        final Region.Id region = regions.get(0);
        assertThat(region.getId()).isEqualTo(regionId().getId());
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(regionRepository.clusters(any())).willReturn(ImmutableList.of(clusterId()));

        final List<Cluster.Id> clusters = defaultRegionService.clusters(region().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(clusterId().getId());
    }
}