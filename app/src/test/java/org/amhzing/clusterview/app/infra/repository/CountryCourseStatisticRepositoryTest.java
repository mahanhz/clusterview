package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.infra.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.app.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

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

        given(countryJpaRepository.findOne(any(String.class))).willReturn(countryEntity());

        final CourseStatistic stats = countryCourseStatisticRepository.statistics(Country.Id.create(countryEntity().getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
    }
}