package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.infra.exception.NotFoundException;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.entity.CourseEntity;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.amhzing.clusterview.data.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultClusterRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    @Mock
    private ClusterJpaRepository clusterJpaRepository;

    private DefaultClusterRepository defaultClusterRepository;

    @Before
    public void setUp() throws Exception {
        defaultClusterRepository = new DefaultClusterRepository(countryJpaRepository, clusterJpaRepository);
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(countryJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.countryEntity());

        final List<Cluster.Id> clusters = defaultClusterRepository.clusters(country().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(JpaRepositoryHelper.clusterEntity().getId());
    }

    @Test
    public void should_save_course_stats() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.clusterEntity());

        defaultClusterRepository.saveCourseStats(cluster().getId(), updatedCourseStatistic());

        verify(clusterJpaRepository, times(1)).save(updatedClusterEntity());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_save_course_stats() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(null);

        defaultClusterRepository.saveCourseStats(cluster().getId(), updatedCourseStatistic());

        fail("How did we get this far?");
    }

    private ClusterEntity updatedClusterEntity() {
        final Map<CourseEntity, Integer> courses = ClusterEntityFactory.courses(updatedCourseStatistic());

        final ClusterEntity clusterEntity = JpaRepositoryHelper.clusterEntity();
        clusterEntity.getCourses().clear();
        clusterEntity.getCourses().putAll(courses);

        return clusterEntity;
    }
}