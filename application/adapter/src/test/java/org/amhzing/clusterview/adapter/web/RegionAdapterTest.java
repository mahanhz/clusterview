package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.RegionDTO;
import org.amhzing.clusterview.core.boundary.enter.RegionService;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.clusterId;
import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.region;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RegionAdapterTest {

    @Mock
    private RegionService regionService;

    private RegionAdapter regionAdapter;

    @Before
    public void setUp() throws Exception {
        regionAdapter = new RegionAdapter(regionService);
    }

    @Test
    public void regions() throws Exception {
        given(regionService.regions(any(Country.Id.class))).willReturn(ImmutableList.of(region().getId()));

        final List<RegionDTO> regions = regionAdapter.regions("se");

        assertThat(regions).isNotEmpty();

        final RegionDTO regionDTO = regions.get(0);
        assertThat(regionDTO.name).isEqualTo(region().getId().getId());
    }

    @Test
    public void clusters() throws Exception {
        given(regionService.clusters(any(Region.Id.class))).willReturn(ImmutableList.of(clusterId()));

        final List<ClusterDTO> clusters = regionAdapter.clusters("central");

        assertThat(clusters).isNotEmpty();

        final ClusterDTO clusterDTO = clusters.get(0);
        assertThat(clusterDTO.name).isEqualTo(clusterId().getId());
    }

}