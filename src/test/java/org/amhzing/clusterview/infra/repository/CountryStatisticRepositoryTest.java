package org.amhzing.clusterview.infra.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.countryEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

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

        given(countryJpaRepository.findOne(any(String.class))).willReturn(countryEntity());
        given(groupRepository.groups(any())).willReturn(ImmutableSet.of(group()));

        final ActivityStatistic stats = countryStatisticRepository.statistics(Country.Id.create(countryEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}