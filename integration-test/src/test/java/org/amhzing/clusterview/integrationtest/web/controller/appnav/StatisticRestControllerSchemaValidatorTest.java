package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.boundary.enter.ClusterService;
import org.amhzing.clusterview.boundary.enter.StatisticHistoryService;
import org.amhzing.clusterview.boundary.enter.StatisticService;
import org.amhzing.clusterview.controller.appnav.StatisticRestController;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.helper.RestHelper;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.ACTIVITY_STATS;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.COURSE_STATS;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.HISTORY;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.courseStatistic;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.datedActivityStatistic;
import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticRestController.class)
@TestOffline
public class StatisticRestControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticService<ActivityStatistic> activityStatisticService;
    @MockBean
    private StatisticService<CourseStatistic> courseStatisticService;
    @MockBean
    private StatisticHistoryService statisticHistoryService;
    @MockBean
    private ClusterService clusterService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_country_schema() throws Exception {

        given(activityStatisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_region_schema() throws Exception {

        given(activityStatisticService.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/central/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_cluster_schema() throws Exception {

        given(activityStatisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/central/cluster1/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_country_schema() throws Exception {

        given(courseStatisticService.statistics(any(Country.Id.class))).willReturn(courseStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_region_schema() throws Exception {

        given(courseStatisticService.statistics(any(Region.Id.class))).willReturn(courseStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/northern/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_cluster_schema() throws Exception {

        given(courseStatisticService.statistics(any(Cluster.Id.class))).willReturn(courseStatistic());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/northern/cluster1/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_stats_history_cluster_schema() throws Exception {

        given(activityStatisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());
        given(statisticHistoryService.history(any(Cluster.Id.class))).willReturn(ImmutableList.of(datedActivityStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview" + HISTORY + "/se/cluster1");

        assertSuccessfulSchemaValidation(result, "HistoricalActivitiesDTO.json");
    }
}