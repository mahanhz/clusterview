package org.amhzing.clusterview.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CountryStatisticRepositoryTest {

    @Mock
    private CountryJpaRepository countryJpaRepository;

    @Mock
    private GroupRepository groupRepository;

    private CountryStatisticRepository countryStatisticRepository;

    @Before
    public void setUp() throws Exception {
        countryStatisticRepository = new CountryStatisticRepository(countryJpaRepository, groupRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(countryJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.countryEntity());
        given(groupRepository.groups(any())).willReturn(ImmutableSet.of(group()));

        final ActivityStatistic stats = countryStatisticRepository.statistics(Country.Id.create(JpaRepositoryHelper.countryEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}