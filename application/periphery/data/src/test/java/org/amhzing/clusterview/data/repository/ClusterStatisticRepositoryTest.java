package org.amhzing.clusterview.data.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.data.helper.DomainModelHelper.group;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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