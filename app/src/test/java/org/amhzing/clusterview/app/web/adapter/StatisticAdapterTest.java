package org.amhzing.clusterview.app.web.adapter;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.helper.ClientModelHelper;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.ClusterNameModel;
import org.amhzing.clusterview.app.web.model.CourseStatisticModel;
import org.amhzing.clusterview.app.web.model.DatedActivityStatisticModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StatisticAdapterTest {

    @Mock
    private StatisticService<ActivityStatistic> statisticService;
    @Mock
    private StatisticService<CourseStatistic> courseStatisticService;
    @Mock
    private StatisticHistoryService statisticHistoryService;
    @Mock
    private ActivityService activityService;
    @Mock
    private ClusterService clusterService;

    private StatisticAdapter statisticAdapter;

    @Before
    public void setUp() throws Exception {
        statisticAdapter = new StatisticAdapter(statisticService,
                                                courseStatisticService,
                                                statisticHistoryService,
                                                activityService,
                                                clusterService);
    }

    @Test
    public void should_get_country_stats() throws Exception {

        given(statisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.countryStats(country().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity().getName())).isEqualTo(quantity().getValue());
    }

    @Test
    public void should_get_country_course_stats() throws Exception {

        given(courseStatisticService.statistics(any(Country.Id.class))).willReturn(courseStatistic());

        final List<CourseStatisticModel> stats = statisticAdapter.countryCourseStats(country().getId().getId());

        assertThat(stats).isNotEmpty();
    }

    @Test
    public void should_get_region_stats() throws Exception {

        given(statisticService.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.regionStats(region().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity().getName())).isEqualTo(quantity().getValue());
    }

    @Test
    public void should_get_region_course_stats() throws Exception {

        given(courseStatisticService.statistics(any(Region.Id.class))).willReturn(courseStatistic());

        final List<CourseStatisticModel> stats = statisticAdapter.regionCourseStats(region().getId().getId());

        assertThat(stats).isNotEmpty();
    }

    @Test
    public void should_get_cluster_stats() throws Exception {

        given(statisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ActivityStatisticModel stats = statisticAdapter.clusterStats(cluster().getId().getId());

        assertThat(stats.getActivityQuantity()).isNotEmpty();
        assertThat(stats.getActivityQuantity().get(activity().getName())).isEqualTo(quantity().getValue());
    }

    @Test
    public void should_get_cluster_course_stats() throws Exception {

        given(courseStatisticService.statistics(any(Cluster.Id.class))).willReturn(courseStatistic());

        final List<CourseStatisticModel> stats = statisticAdapter.clusterCourseStats(cluster().getId().getId());

        assertThat(stats).isNotEmpty();
    }

    @Test
    public void should_get_clusters() throws Exception {

        given(clusterService.clusters(any())).willReturn(ImmutableList.of(cluster().getId()));

        final List<ClusterNameModel> clusters = statisticAdapter.clusters("se");

        assertThat(clusters).isNotEmpty();

        final ClusterNameModel clusterNameModel = clusters.get(0);
        assertThat(clusterNameModel.getName()).isEqualTo(cluster().getId().getId());
    }

    @Test
    public void should_get_stats_history() throws Exception {

        given(statisticHistoryService.history(any())).willReturn(ImmutableList.of(datedActivityStatistic()));

        final List<DatedActivityStatisticModel> datedActivityStats = statisticAdapter.statsHistory("stockholm");

        assertThat(datedActivityStats).isNotEmpty();

        final DatedActivityStatisticModel datedActivityStatisticModel = datedActivityStats.get(0);
        assertThat(datedActivityStatisticModel.getDate()).isEqualToIgnoringHours(datedActivityStatistic().getDate());
    }

    @Test
    public void should_save_history() throws Exception {

        final Cluster.Id clusterId = cluster().getId();

        statisticAdapter.saveStatsHistory(clusterId.getId(), ClientModelHelper.activityStatisticModel());

        verify(statisticHistoryService, times(1)).saveHistory(eq(clusterId), any(ActivityStatistic.class));
    }
}