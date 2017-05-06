package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.domain.Region;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;
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
public class RegionCourseStatisticRepositoryTest {

    @Mock
    private RegionJpaRepository regionJpaRepository;

    private RegionCourseStatisticRepository regionStatisticRepository;

    @Before
    public void setUp() throws Exception {
        regionStatisticRepository = new RegionCourseStatisticRepository(regionJpaRepository);
    }

    @Test
    public void should_get_statistics() throws Exception {

        given(regionJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.regionEntity());

        final CourseStatistic stats = regionStatisticRepository.statistics(Region.Id.create(JpaRepositoryHelper.regionEntity().getId()));

        assertThat(stats.getCourseQuantity()).isNotEmpty();
    }
}