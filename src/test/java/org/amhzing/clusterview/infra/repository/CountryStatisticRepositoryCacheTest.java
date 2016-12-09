package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.configuration.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class CountryStatisticRepositoryCacheTest {

    private static final String EXISTING = "existing";

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private StatisticRepository<Country.Id, ActivityStatistic> statisticRepository;

    @Test
    public void should_invoke_cache() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(countryEntity());

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final ActivityStatistic firstCall = statisticRepository.statistics(countryId);

        // Second call
        final ActivityStatistic secondCall = statisticRepository.statistics(countryId);


        assertThat(firstCall).isSameAs(secondCall);

        verify(countryJpaRepository, times(1)).findOne(countryId.getId());
    }
}