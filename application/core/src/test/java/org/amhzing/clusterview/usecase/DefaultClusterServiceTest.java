package org.amhzing.clusterview.usecase;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.boundary.exit.repository.ClusterRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void should_update_group() throws Exception {

        final CourseStatistic courseStatistic = courseStatistic();
        final Cluster.Id clusterId = cluster().getId();

        defaultClusterService.saveCourseStats(clusterId, courseStatistic);

        verify(clusterRepository, times(1)).saveCourseStats(clusterId, courseStatistic);
    }
}