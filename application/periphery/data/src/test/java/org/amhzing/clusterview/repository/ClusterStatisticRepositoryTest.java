package org.amhzing.clusterview.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.repository.ClusterJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClusterStatisticRepositoryTest {

    @Mock
    private ClusterJpaRepository clusterJpaRepository;

    @Mock
    private GroupRepository groupRepository;

    private ClusterStatisticRepository clusterStatisticRepository;

    @Before
    public void setUp() throws Exception {
        clusterStatisticRepository = new ClusterStatisticRepository(clusterJpaRepository, groupRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.clusterEntity());
        given(groupRepository.groups(any())).willReturn(ImmutableSet.of(group()));

        final ActivityStatistic stats = clusterStatisticRepository.statistics(Cluster.Id.create(JpaRepositoryHelper.clusterEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}