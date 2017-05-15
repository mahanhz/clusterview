package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.ClustersDTO;
import org.amhzing.clusterview.core.boundary.enter.ClusterService;
import org.amhzing.clusterview.core.domain.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.clusterId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ClusterAdapterTest {

    @Mock
    private ClusterService clusterService;

    private ClusterAdapter clusterAdapter;

    @Before
    public void setUp() throws Exception {
        clusterAdapter = new ClusterAdapter(clusterService);
    }

    @Test
    public void clusters() throws Exception {
        given(clusterService.clusters(any(Country.Id.class))).willReturn(ImmutableList.of(clusterId()));
        final ClustersDTO clusters = clusterAdapter.clusters("se");

        assertThat(clusters.clusters).isNotEmpty();

        final ClusterDTO clusterDTO = clusters.clusters.get(0);
        assertThat(clusterDTO.name).isEqualTo(clusterId().getId());
    }

}