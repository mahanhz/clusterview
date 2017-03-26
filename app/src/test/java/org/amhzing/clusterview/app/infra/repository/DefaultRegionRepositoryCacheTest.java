package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.repository.RegionRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.RegionJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.countryEntity;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.regionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultRegionRepositoryCacheTest {

    private static final String EXISTING = "existing";
    public static final String CACHE_TEST_VALUE = "value";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_country_cache() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(countryEntity());

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final List<Region.Id> firstCall = regionRepository.regions(countryId);

        // Second call
        final List<Region.Id> secondCall = regionRepository.regions(countryId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(countryJpaRepository, times(1)).findOne(countryId.getId());
    }

    @Test
    public void should_invoke_region_cache() throws Exception {

        given(regionJpaRepository.findOne(EXISTING)).willReturn(regionEntity());

        final Region.Id regionId = Region.Id.create(EXISTING);

        // First call
        final List<Cluster.Id> firstCall = regionRepository.clusters(regionId);

        // Second call
        final List<Cluster.Id> secondCall = regionRepository.clusters(regionId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(regionJpaRepository, times(1)).findOne(regionId.getId());
    }

    @Test
    public void should_not_invoke_country_cache_when_empty() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(null);

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final List<Region.Id> firstCall = regionRepository.regions(countryId);

        // Second call
        final List<Region.Id> secondCall = regionRepository.regions(countryId);

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();

        verify(countryJpaRepository, times(2)).findOne(countryId.getId());
    }

    @Test
    public void should_not_invoke_region_cache_when_empty() throws Exception {

        given(regionJpaRepository.findOne(EXISTING)).willReturn(null);

        final Region.Id regionId = Region.Id.create(EXISTING);

        // First call
        final List<Cluster.Id> firstCall = regionRepository.clusters(regionId);

        // Second call
        final List<Cluster.Id> secondCall = regionRepository.clusters(regionId);

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();

        verify(regionJpaRepository, times(2)).findOne(regionId.getId());
    }
}