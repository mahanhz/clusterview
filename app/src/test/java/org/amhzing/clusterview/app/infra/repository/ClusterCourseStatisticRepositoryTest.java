package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.clusterEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClusterCourseStatisticRepositoryTest {

    @Mock
    private ClusterJpaRepository clusterJpaRepository;

    private ClusterCourseStatisticRepository clusterCourseStatisticRepository;

    @Before
    public void setUp() throws Exception {
        clusterCourseStatisticRepository = new ClusterCourseStatisticRepository(clusterJpaRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

        final CourseStatistic stats = clusterCourseStatisticRepository.statistics(Cluster.Id.create(clusterEntity().getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
    }
}