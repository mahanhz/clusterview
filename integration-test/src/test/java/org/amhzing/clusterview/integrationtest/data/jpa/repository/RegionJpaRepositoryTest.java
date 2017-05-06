package org.amhzing.clusterview.integrationtest.data.jpa.repository;

import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.jpa.entity.RegionEntity;
import org.amhzing.clusterview.jpa.repository.RegionJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class RegionJpaRepositoryTest {

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Test
    public void should_get_cluster() throws Exception {
        final RegionEntity region = regionJpaRepository.findOne("central");

        assertThat(region).isNotNull();
        assertThat(region.getId()).isEqualToIgnoringCase("central");
        assertThat(region.getClusters()).hasSize(7);
    }
}