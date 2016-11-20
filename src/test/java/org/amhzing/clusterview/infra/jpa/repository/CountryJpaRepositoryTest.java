package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Country;
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
public class CountryJpaRepositoryTest {

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_get_country() throws Exception {
        final Country country = countryJpaRepository.findOne("se");

        assertThat(country).isNotNull();
        assertThat(country.getId()).isEqualToIgnoringCase("se");
        assertThat(country.getRegions()).hasSize(3);
    }
}