package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.testconfig.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultClusterRepositoryCacheTest {

    private static final String EXISTING = "existing";

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private ClusterRepository clusterRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(countryEntity());

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
}