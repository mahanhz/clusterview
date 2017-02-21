package org.amhzing.clusterview.app.infra.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.app.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.repository.GroupRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

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