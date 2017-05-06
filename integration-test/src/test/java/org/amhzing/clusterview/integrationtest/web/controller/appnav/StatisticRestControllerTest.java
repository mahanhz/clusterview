package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
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
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.ACTIVITY_STATS;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.COURSE_STATS;
import static org.amhzing.clusterview.controller.appnav.StatisticRestController.HISTORY;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticRestController.class)
@TestOffline
public class StatisticRestControllerTest {

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
    public void should_get_activity_stats_links_for_country() throws Exception {

        given(activityStatisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());

        final Object document = parseJson(mvc, BASE_PATH + "/statsview/se/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activity_stats_links_for_region() throws Exception {

        given(activityStatisticService.statistics(any(Region.Id.class))).willReturn(activityStatistic());

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/central/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY, REGION_PREFIX + "central");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activity_stats_links_for_cluster() throws Exception {

        given(activityStatisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/central/stockholm/" + ACTIVITY_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY,
                                                   REGION_PREFIX + "central", CLUSTER_PREFIX + "stockholm");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_country() throws Exception {

        given(courseStatisticService.statistics(any(Country.Id.class))).willReturn(courseStatistic());

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_region() throws Exception {

        given(courseStatisticService.statistics(any(Region.Id.class))).willReturn(courseStatistic());

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/northern/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY, REGION_PREFIX + "northern");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_course_stats_links_for_cluster() throws Exception {

        given(courseStatisticService.statistics(any(Cluster.Id.class))).willReturn(courseStatistic());

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview/se/northern/cluster1/" + COURSE_STATS);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY, REL_STATS_HISTORY,
                                                   REGION_PREFIX + "northern", CLUSTER_PREFIX + "cluster1");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_stats_history_links_for_country() throws Exception {

        given(courseStatisticService.statistics(any(Country.Id.class))).willReturn(courseStatistic());
        given(clusterService.clusters(any(Country.Id.class))).willReturn(clustersIds());

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

        given(activityStatisticService.statistics(any(Cluster.Id.class))).willReturn(activityStatistic());
        given(statisticHistoryService.history(any(Cluster.Id.class))).willReturn(ImmutableList.of(datedActivityStatistic()));

        final Object document =  parseJson(mvc, BASE_PATH + "/statsview" + HISTORY + "/se/cluster1");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   REL_STATS_HISTORY,
                                                   REL_STATS_SAVE_HISTORY);
    }
}