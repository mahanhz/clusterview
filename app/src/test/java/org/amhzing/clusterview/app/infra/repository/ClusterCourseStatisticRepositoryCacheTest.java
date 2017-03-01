package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.StatisticRepository;
import org.amhzing.clusterview.app.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.app.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class ClusterCourseStatisticRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private StatisticRepository<Cluster.Id, CourseStatistic> courseStatisticRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;


    @Test
    public void should_invoke_cache() throws Exception {

        given(clusterJpaRepository.findOne(EXISTING_CLUSTER)).willReturn(JpaRepositoryHelper.clusterEntity());

        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER);

        // First call
        final CourseStatistic firstCall = courseStatisticRepository.statistics(clusterId);

        // Second call
        final CourseStatistic secondCall = courseStatisticRepository.statistics(clusterId);


        assertThat(firstCall).isSameAs(secondCall);

        verify(clusterJpaRepository, times(1)).findOne(clusterId.getId());
    }
}