package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.RegionJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.data.helper.DomainModelHelper.country;
import static org.amhzing.clusterview.data.helper.DomainModelHelper.region;
import static org.amhzing.clusterview.data.helper.JpaRepositoryHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRegionRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    @Mock
    private RegionJpaRepository regionJpaRepository;

    private DefaultRegionRepository defaultRegionRepository;

    @Before
    public void setUp() throws Exception {
        defaultRegionRepository = new DefaultRegionRepository(countryJpaRepository, regionJpaRepository);
    }

    @Test
    public void should_get_regions() throws Exception {

        given(countryJpaRepository.findOne(any(String.class))).willReturn(countryEntity());

        final List<Region.Id> regions = defaultRegionRepository.regions(country().getId());

        assertThat(regions).isNotEmpty();

        final Region.Id region = regions.get(0);
        assertThat(region.getId()).isEqualTo(regionEntity().getId());
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(regionJpaRepository.findOne(any(String.class))).willReturn(regionEntity());

        final List<Cluster.Id> clusters = defaultRegionRepository.clusters(region().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(clusterEntity().getId());
    }
}