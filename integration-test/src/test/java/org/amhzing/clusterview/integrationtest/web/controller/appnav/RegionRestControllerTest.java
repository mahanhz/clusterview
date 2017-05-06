package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.boundary.enter.RegionService;
import org.amhzing.clusterview.controller.appnav.RegionRestController;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Region;
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
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(RegionRestController.class)
@TestOffline
public class RegionRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionService regionService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_links() throws Exception {

        given(regionService.clusters(any(Region.Id.class))).willReturn(ImmutableList.of(Cluster.Id.create("cluster1"),
                                                                                        Cluster.Id.create("cluster2"),
                                                                                        Cluster.Id.create("cluster3")));

        final Object document = parseJson(mvc, BASE_PATH + "/clusterview/se/central");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   CLUSTER_PREFIX + "cluster1",
                                                   CLUSTER_PREFIX + "cluster2",
                                                   CLUSTER_PREFIX + "cluster3",
                                                   REL_STATS_ACTIVITY, REL_STATS_COURSE, REL_STATS_HISTORY);
    }
}