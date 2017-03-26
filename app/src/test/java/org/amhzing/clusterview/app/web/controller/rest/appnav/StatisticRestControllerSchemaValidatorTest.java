package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.helper.RestHelper;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.app.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

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