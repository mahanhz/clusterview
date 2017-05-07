package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.ClusterRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.data.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.data.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.data.cache.CacheSpec.STATS_COURSE_CACHE_NAME;
import static org.amhzing.clusterview.data.helper.DomainModelHelper.courseStatistic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultClusterRepositoryCacheTest {

    private static final String EXISTING = "existing";
    public static final String CACHE_TEST_VALUE = "value";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private ClusterRepository clusterRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(JpaRepositoryHelper.countryEntity());

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final List<Cluster.Id> firstCall = clusterRepository.clusters(countryId);

        // Second call
        final List<Cluster.Id> secondCall = clusterRepository.clusters(countryId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(countryJpaRepository, times(1)).findOne(countryId.getId());
    }

    @Test
    public void should_not_invoke_cache_when_empty() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(null);

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final List<Cluster.Id> firstCall = clusterRepository.clusters(countryId);

        // Second call
        final List<Cluster.Id> secondCall = clusterRepository.clusters(countryId);

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();

        verify(countryJpaRepository, times(2)).findOne(countryId.getId());
    }

    @Test
    public void should_evict_caches_when_saving_course_stats() throws Exception {

        givenAPopulatedCache();

        whenSavingCourseStats();

        // stats cache is cleared
        assertThat(cacheValue(STATS_COURSE_CACHE_NAME, DefaultGroupRepositoryCacheTest.CACHE_TEST_KEY)).isNull();
    }

    private void givenAPopulatedCache() {
        given(clusterJpaRepository.findOne(EXISTING)).willReturn(JpaRepositoryHelper.clusterEntity());
        cacheManager.getCache(STATS_COURSE_CACHE_NAME).put(statsCacheKey(), CACHE_TEST_VALUE);

        assertThat(cacheValue(STATS_COURSE_CACHE_NAME, statsCacheKey())).isEqualTo(CACHE_TEST_VALUE);
    }

    private void whenSavingCourseStats() {
        when(clusterJpaRepository.save(any(ClusterEntity.class))).thenReturn(JpaRepositoryHelper.clusterEntity());
        clusterRepository.saveCourseStats(clusterId(), courseStatistic());
    }

    private Cluster.Id clusterId() {
        return Cluster.Id.create(EXISTING);
    }

    private String statsCacheKey() {
        return STATS_COURSE_CACHE_NAME + '_' + clusterId();
    }

    private String cacheValue(final String name, final String key) {
        final org.springframework.cache.Cache.ValueWrapper valueWrapper = cacheManager.getCache(name).get(key);

        if (valueWrapper == null) {
            return null;
        }

        return (String) valueWrapper.get();
    }
}