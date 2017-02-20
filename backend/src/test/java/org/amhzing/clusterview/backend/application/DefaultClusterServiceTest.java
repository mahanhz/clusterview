package org.amhzing.clusterview.backend.application;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.repository.ClusterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.backend.helper.DomainModelHelper.clusterId;
import static org.amhzing.clusterview.backend.helper.DomainModelHelper.country;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DefaultClusterServiceTest {

    @Mock
    private ClusterRepository clusterRepository;

    private DefaultClusterService defaultClusterService;

    @Before
    public void setUp() throws Exception {
        defaultClusterService = new DefaultClusterService(clusterRepository);
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(clusterRepository.clusters(any())).willReturn(ImmutableList.of(clusterId()));

        final List<Cluster.Id> clusters = defaultClusterService.clusters(country().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(clusterId().getId());
    }
}