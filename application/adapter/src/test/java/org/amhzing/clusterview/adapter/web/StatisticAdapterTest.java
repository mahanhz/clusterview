package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.CoursesDTO;
import org.amhzing.clusterview.adapter.web.api.statistic.HistoricalActivitiesDTO;
import org.amhzing.clusterview.core.boundary.enter.StatisticHistoryService;
import org.amhzing.clusterview.core.boundary.enter.StatisticService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.adapter.web.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StatisticAdapterTest {

    @Mock
    private StatisticHistoryService statisticHistoryService;
    @Mock
    private StatisticService<ActivityStatistic> statisticService;
    @Mock
    private StatisticService<CourseStatistic> courseStatisticService;

    private StatisticAdapter statisticAdapter;

    @Before
    public void setUp() throws Exception {
        statisticAdapter = new StatisticAdapter(statisticHistoryService,  statisticService, courseStatisticService);
    }

    @Test
    public void countryActivityStatistics() throws Exception {
        given(statisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ActivitiesDTO activitiesDTO = statisticAdapter.countryActivityStatistics("se");

        assertThat(activitiesDTO.activities).isNotEmpty();
    }

    @Test
    public void countryCourseStatistics() throws Exception {
        given(courseStatisticService.statistics(any(Country.Id.class))).willReturn(courseStatistic());

        final CoursesDTO coursesDTO = statisticAdapter.countryCourseStatistics("se");

        assertThat(coursesDTO.courses).isNotEmpty();
    }

    @Test
    public void regionActivityStatistics() throws Exception {
        given(statisticService.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ActivitiesDTO activitiesDTO = statisticAdapter.regionActivityStatistics("central");

        assertThat(activitiesDTO.activities).isNotEmpty();
    }

    @Test
    public void regionCourseStatistics() throws Exception {
        given(courseStatisticService.statistics(any(Region.Id.class))).willReturn(courseStatistic());

        final CoursesDTO coursesDTO = statisticAdapter.regionCourseStatistics("central");

        assertThat(coursesDTO.courses).isNotEmpty();
    }

    @Test
    public void clusterActivityStatistics() throws Exception {
        given(statisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ActivitiesDTO activitiesDTO = statisticAdapter.clusterActivityStatistics("cluster1");

        assertThat(activitiesDTO.activities).isNotEmpty();
    }

    @Test
    public void clusterCourseStatistics() throws Exception {
        given(courseStatisticService.statistics(any(Cluster.Id.class))).willReturn(courseStatistic());

        final CoursesDTO coursesDTO = statisticAdapter.clusterCourseStatistics("cluster1");

        assertThat(coursesDTO.courses).isNotEmpty();
    }

    @Test
    public void clusterHistory() throws Exception {
        given(statisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());
        given(statisticHistoryService.history(any(Cluster.Id.class))).willReturn(ImmutableList.of(datedActivityStatistic()));

        final HistoricalActivitiesDTO clusterHistory = statisticAdapter.clusterHistory("cluster1");

        assertThat(clusterHistory.datedActivitiesDTO).isNotEmpty();
    }

    @Test
    public void saveHistory() throws Exception {
        given(statisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final Cluster.Id clusterId = cluster().getId();

        statisticAdapter.saveHistory(clusterId.getId());

        verify(statisticHistoryService, times(1)).saveHistory(eq(clusterId), ArgumentMatchers.any(ActivityStatistic.class));
    }

}