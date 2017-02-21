package org.amhzing.clusterview.backend.infra.repository;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.backend.infra.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.backend.helper.DomainModelHelper.country;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DefaultClusterRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    private DefaultClusterRepository defaultClusterRepository;

    @Before
    public void setUp() throws Exception {
        defaultClusterRepository = new DefaultClusterRepository(countryJpaRepository);
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(countryJpaRepository.findOne(Matchers.any(String.class))).willReturn(JpaRepositoryHelper.countryEntity());

        final List<Cluster.Id> clusters = defaultClusterRepository.clusters(country().getId());

        assertThat(clusters).isNotEmpty();

        final Cluster.Id cluster = clusters.get(0);
        assertThat(cluster.getId()).isEqualTo(JpaRepositoryHelper.clusterEntity().getId());
    }
}