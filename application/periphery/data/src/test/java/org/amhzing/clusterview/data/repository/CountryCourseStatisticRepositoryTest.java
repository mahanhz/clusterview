package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CountryCourseStatisticRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    private CountryCourseStatisticRepository countryCourseStatisticRepository;

    @Before
    public void setUp() throws Exception {
        countryCourseStatisticRepository = new CountryCourseStatisticRepository(countryJpaRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(countryJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.countryEntity());

        final CourseStatistic stats = countryCourseStatisticRepository.statistics(Country.Id.create(JpaRepositoryHelper.countryEntity().getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
    }
}