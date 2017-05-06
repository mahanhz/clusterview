package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.core.domain.statistic.Quantity;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.CourseJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.data.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.data.helper.JpaRepositoryHelper.courses;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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