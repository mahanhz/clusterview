package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClusterStatisticRepositoryTest {

    @Mock
    private ClusterJpaRepository clusterJpaRepository;

    @InjectMocks
    private ClusterStatisticRepository clusterStatisticRepository;

    @Test
    public void should_get_statistics() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

        final ActivityStatistic stats = clusterStatisticRepository.statistics(Cluster.Id.create(clusterEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}