package org.amhzing.clusterview.data.jpa.repository;

import org.amhzing.clusterview.data.jpa.entity.CountryEntity;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class CountryJpaRepositoryTest {

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Test
    public void should_get_country() throws Exception {
        final CountryEntity country = countryJpaRepository.findOne("se");

        assertThat(country).isNotNull();
        assertThat(country.getId()).isEqualToIgnoringCase("se");
        assertThat(country.getRegions()).hasSize(3);
    }
}