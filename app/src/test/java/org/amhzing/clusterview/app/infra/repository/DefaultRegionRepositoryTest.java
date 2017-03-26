package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.RegionJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.country;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.region;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.countryEntity;
import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.regionEntity;
import static org.assertj.core.api.Assertions.assertThat;
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

        given(countryJpaRepository.findOne(Matchers.any(String.class))).willReturn(countryEntity());

        final List<Region.Id> regions = defaultRegionRepository.regions(country().getId());

        assertThat(regions).isNotEmpty();

        final Region.Id region = regions.get(0);
        assertThat(region.getId()).isEqualTo(regionEntity().getId());
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(regionJpaRepository.findOne(Matchers.any(String.class))).willReturn(regionEntity());

        final List<Cluster.Id> clusters = defaultRegionRepository.clusters(region().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(clusterEntity().getId());
    }
}