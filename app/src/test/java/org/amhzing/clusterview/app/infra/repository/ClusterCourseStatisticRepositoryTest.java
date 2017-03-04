package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.CourseJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.courses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClusterCourseStatisticRepositoryTest {

    @Mock
    private ClusterJpaRepository clusterJpaRepository;
    @Mock
    private CourseJpaRepository courseJpaRepository;

    private ClusterCourseStatisticRepository clusterCourseStatisticRepository;

    @Before
    public void setUp() throws Exception {
        clusterCourseStatisticRepository = new ClusterCourseStatisticRepository(clusterJpaRepository, courseJpaRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

        final CourseStatistic stats = clusterCourseStatisticRepository.statistics(Cluster.Id.create(clusterEntity().getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
    }

    @Test
    public void should_create_zero_valued_courses_when_empty() throws Exception {

        final ClusterEntity clusterWithoutCourses = clusterEntity();
        clusterWithoutCourses.getCourses().clear();

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterWithoutCourses);
        given(courseJpaRepository.findAll()).willReturn(courses().keySet().stream().collect(toList()));

        final CourseStatistic stats = clusterCourseStatisticRepository.statistics(Cluster.Id.create(clusterWithoutCourses.getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
        assertThat(stats.getCourseQuantity().values()).containsOnly(Quantity.create(0));
    }
}