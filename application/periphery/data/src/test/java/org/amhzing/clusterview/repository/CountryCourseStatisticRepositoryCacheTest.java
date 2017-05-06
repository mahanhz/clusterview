package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.boundary.exit.repository.StatisticRepository;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.testconfig.CacheTestConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class CountryCourseStatisticRepositoryCacheTest {

    private static final String EXISTING = "existing";

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private StatisticRepository<Country.Id, CourseStatistic> statisticRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(countryJpaRepository.findOne(EXISTING)).willReturn(JpaRepositoryHelper.countryEntity());

        final Country.Id countryId = Country.Id.create(EXISTING);

        // First call
        final CourseStatistic firstCall = statisticRepository.statistics(countryId);

        // Second call
        final CourseStatistic secondCall = statisticRepository.statistics(countryId);

        assertThat(firstCall).isSameAs(secondCall);

        verify(countryJpaRepository, times(1)).findOne(countryId.getId());
    }
}