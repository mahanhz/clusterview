package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.exception.NotFoundException;
import org.amhzing.clusterview.app.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.CourseEntity;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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

        given(countryJpaRepository.findOne(Matchers.any(String.class))).willReturn(countryEntity());

        final List<Cluster.Id> clusters = defaultClusterRepository.clusters(country().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(clusterEntity().getId());
    }

    @Test
    public void should_save_course_stats() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

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

        final ClusterEntity clusterEntity = clusterEntity();
        clusterEntity.getCourses().clear();
        clusterEntity.getCourses().putAll(courses);

        return clusterEntity;
    }
}