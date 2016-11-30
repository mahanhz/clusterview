package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.infra.jpa.repository.RegionJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.regionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RegionStatisticRepositoryTest {

    @Mock
    private RegionJpaRepository regionJpaRepository;

    @InjectMocks
    private RegionStatisticRepository regionStatisticRepository;

    @Test
    public void should_get_statistics() throws Exception {

        given(regionJpaRepository.findOne(any(String.class))).willReturn(regionEntity());

        final ActivityStatistic stats = regionStatisticRepository.statistics(Region.Id.create(regionEntity().getId()));

        assertThat(stats.getActivityQuantity()).isNotEmpty();
    }
}