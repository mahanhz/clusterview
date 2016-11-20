package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class RegionJpaRepositoryTest {

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_get_cluster() throws Exception {
        final Region region = regionJpaRepository.findOne("central");

        assertThat(region).isNotNull();
        assertThat(region.getId()).isEqualToIgnoringCase("central");
        assertThat(region.getClusters()).hasSize(2);
    }
}