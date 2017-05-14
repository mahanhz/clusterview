package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.StatisticAdapter;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.controller.appnav.StatisticRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.amhzing.clusterview.adapter.web.util.ClusterDtoFactory.clustersDTO;
import static org.amhzing.clusterview.adapter.web.util.StatisticFactory.*;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.COUNTRY;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.web.controller.appnav.StatisticRestController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticRestController.class)
@TestOffline
public class StatisticRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;
    @MockBean
    private ClusterAdapter clusterAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activity_stats_links_for_country() throws Exception {

        given(statisticAdapter.countryActivityStatistics(COUNTRY)).willReturn(activitiesDto(activityStatistic()));

        final Object document = parseJson(mvc, BASE_PATH + "/statsview/se/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activity_stats_links_for_region() throws Exception {

        given(statisticAdapter.regionActivityStatistics("central")).willReturn(activitiesDto(activityStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/central/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY, REGION_PREFIX + "central");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activity_stats_links_for_cluster() throws Exception {

        given(statisticAdapter.clusterActivityStatistics("stockholm")).willReturn(activitiesDto(activityStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/central/stockholm/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY,
                                                   REGION_PREFIX + "central", CLUSTER_PREFIX + "stockholm");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_country() throws Exception {

        given(statisticAdapter.countryCourseStatistics(COUNTRY)).willReturn(coursesDto(courseStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_region() throws Exception {

        given(statisticAdapter.regionCourseStatistics("northern")).willReturn(coursesDto(courseStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/northern/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY, REGION_PREFIX + "northern");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_cluster() throws Exception {

        given(statisticAdapter.clusterCourseStatistics("cluster1")).willReturn(coursesDto(courseStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/northern/cluster1/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY,
                                                   REGION_PREFIX + "northern", CLUSTER_PREFIX + "cluster1");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_stats_history_links_for_country() throws Exception {

        given(clusterAdapter.clusters(COUNTRY)).willReturn(clustersDTO(clustersIds()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview" + HISTORY + "/se");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   CLUSTER_STATS_HISTORY_PREFIX + "cluster1",
                                                   CLUSTER_STATS_HISTORY_PREFIX + "cluster2",
                                                   CLUSTER_STATS_HISTORY_PREFIX + "cluster3");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_stats_history_links_for_cluster() throws Exception {

        final ActivityStatistic currentStats = activityStatistic();
        final List<DatedActivityStatistic> datedStats = ImmutableList.of(datedActivityStatistic());

        given(statisticAdapter.clusterHistory("cluster1")).willReturn(historicalActivitiesDto(currentStats, datedStats));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview" + HISTORY + "/se/cluster1");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   REL_STATS_HISTORY,
                                                   REL_STATS_SAVE_HISTORY);
    }
}