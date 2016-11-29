package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CountryStatisticRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    @InjectMocks
    private CountryStatisticRepository countryStatisticRepository;

    @Test
    public void should_get_statistics() throws Exception {

        given(countryJpaRepository.findOne(any(String.class))).willReturn(countryEntity());

        final ActivityStatistic stats = countryStatisticRepository.statistics(Country.Id.create(countryEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}