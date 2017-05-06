package org.amhzing.clusterview.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.domain.Region;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.helper.DomainModelHelper;
import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.repository.RegionJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RegionStatisticRepositoryTest {

    @Mock
    private RegionJpaRepository regionJpaRepository;

    @Mock
    private GroupRepository groupRepository;

    private RegionStatisticRepository regionStatisticRepository;

    @Before
    public void setUp() throws Exception {
        regionStatisticRepository = new RegionStatisticRepository(regionJpaRepository, groupRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(regionJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.regionEntity());
        given(groupRepository.groups(any())).willReturn(ImmutableSet.of(DomainModelHelper.group()));

        final ActivityStatistic stats = regionStatisticRepository.statistics(Region.Id.create(JpaRepositoryHelper.regionEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}