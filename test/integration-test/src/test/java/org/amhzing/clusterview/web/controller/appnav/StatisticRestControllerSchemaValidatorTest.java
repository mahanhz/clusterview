package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.StatisticAdapter;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;
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

import java.util.List;

import static org.amhzing.clusterview.adapter.web.util.StatisticFactory.*;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.COUNTRY;
import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.StatisticRestController.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticRestController.class)
@TestOffline
public class StatisticRestControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;
    @MockBean
    private ClusterAdapter clusterAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_country_schema() throws Exception {

        given(statisticAdapter.countryActivityStatistics(COUNTRY)).willReturn(activitiesDto(activityStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_region_schema() throws Exception {

        given(statisticAdapter.regionActivityStatistics("central")).willReturn(activitiesDto(activityStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/central/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activity_stats_cluster_schema() throws Exception {

        given(statisticAdapter.clusterActivityStatistics("cluster1")).willReturn(activitiesDto(activityStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/central/cluster1/" + ACTIVITY_STATS);

        assertSuccessfulSchemaValidation(result, "ActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_country_schema() throws Exception {

        given(statisticAdapter.countryCourseStatistics(COUNTRY)).willReturn(coursesDto(courseStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_region_schema() throws Exception {

        given(statisticAdapter.regionCourseStatistics("northern")).willReturn(coursesDto(courseStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/northern/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_course_stats_cluster_schema() throws Exception {

        given(statisticAdapter.clusterCourseStatistics("cluster1")).willReturn(coursesDto(courseStatistic()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview/se/northern/cluster1/" + COURSE_STATS);

        assertSuccessfulSchemaValidation(result, "CoursesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_stats_history_cluster_schema() throws Exception {

        final ActivityStatistic currentStats = activityStatistic();
        final List<DatedActivityStatistic> datedStats = ImmutableList.of(datedActivityStatistic());

        given(statisticAdapter.clusterHistory("cluster1")).willReturn(historicalActivitiesDto(currentStats, datedStats));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/statsview" + HISTORY + "/se/cluster1");

        assertSuccessfulSchemaValidation(result, "HistoricalActivitiesDTO.json");
    }
}