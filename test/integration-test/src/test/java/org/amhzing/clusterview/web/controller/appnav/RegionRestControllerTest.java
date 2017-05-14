package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.RegionAdapter;
import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(RegionRestController.class)
@TestOffline
public class RegionRestControllerTest {

    public static final String CLUSTER_1 = "cluster1";
    public static final String CLUSTER_2 = "cluster2";
    public static final String CLUSTER_3 = "cluster3";
    private static final String CENTRAL_REGION = "central";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionAdapter regionAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_links() throws Exception {

        given(regionAdapter.clusters(CENTRAL_REGION)).willReturn(clusters());

        final Object document = parseJson(mvc, BASE_PATH + "/clusterview/se/" + CENTRAL_REGION);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   CLUSTER_PREFIX + CLUSTER_1,
                                                   CLUSTER_PREFIX + CLUSTER_2,
                                                   CLUSTER_PREFIX + CLUSTER_3,
                                                   REL_STATS_ACTIVITY, REL_STATS_COURSE, REL_STATS_HISTORY);
    }

    private List<ClusterDTO> clusters() {
        return ImmutableList.of(clusterDTO(CLUSTER_1),
                                clusterDTO(CLUSTER_2),
                                clusterDTO(CLUSTER_3));
    }

    private ClusterDTO clusterDTO(final String name) {
        return new ClusterDTO(name);
    }
}