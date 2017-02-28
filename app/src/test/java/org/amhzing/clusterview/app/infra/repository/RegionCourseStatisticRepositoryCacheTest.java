package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.CourseStatisticRepository;
import org.amhzing.clusterview.app.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.app.infra.jpa.repository.RegionJpaRepository;
import org.amhzing.clusterview.app.testconfig.CacheInvalidateRule;
import org.amhzing.clusterview.app.testconfig.CacheTestConfig;
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
public class RegionCourseStatisticRepositoryCacheTest {

    private static final String EXISTING_REGION = "existing";

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Autowired
    private CourseStatisticRepository<Region.Id> statisticRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_cache() throws Exception {

        given(regionJpaRepository.findOne(EXISTING_REGION)).willReturn(JpaRepositoryHelper.regionEntity());

        final Region.Id regionId = Region.Id.create(EXISTING_REGION);

        // First call
        final CourseStatistic firstCall = statisticRepository.statistics(regionId);

        // Second call
        final CourseStatistic secondCall = statisticRepository.statistics(regionId);


        assertThat(firstCall).isSameAs(secondCall);

        verify(regionJpaRepository, times(1)).findOne(regionId.getId());
    }
}