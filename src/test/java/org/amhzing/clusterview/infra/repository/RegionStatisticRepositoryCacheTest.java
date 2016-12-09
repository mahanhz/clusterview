package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.configuration.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.repository.RegionJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.regionEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class RegionStatisticRepositoryCacheTest {

    private static final String EXISTING_REGION = "existing";

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Autowired
    private StatisticRepository<Region.Id, ActivityStatistic> statisticRepository;

    @Test
    public void should_invoke_cache() throws Exception {

        given(regionJpaRepository.findOne(EXISTING_REGION)).willReturn(regionEntity());

        final Region.Id regionId = Region.Id.create(EXISTING_REGION);

        // First call
        final ActivityStatistic firstCall = statisticRepository.statistics(regionId);

        // Second call
        final ActivityStatistic secondCall = statisticRepository.statistics(regionId);


        assertThat(firstCall).isSameAs(secondCall);

        verify(regionJpaRepository, times(1)).findOne(regionId.getId());
    }
}