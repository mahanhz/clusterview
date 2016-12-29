package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.testconfig.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class ClusterStatisticRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(clusterJpaRepository.findOne(EXISTING_CLUSTER)).willReturn(clusterEntity());

        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER);

        // First call
        final ActivityStatistic firstCall = clusterStatisticRepository.statistics(clusterId);

        // Second call
        final ActivityStatistic secondCall = clusterStatisticRepository.statistics(clusterId);


        assertThat(firstCall).isSameAs(secondCall);

        verify(clusterJpaRepository, times(1)).findOne(clusterId.getId());
    }
}