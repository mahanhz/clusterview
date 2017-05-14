package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.GroupAdapter;
import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.controller.appnav.GroupRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.amhzing.clusterview.integrationtest.helper.AdapterHelper.groupDTO;
import static org.amhzing.clusterview.integrationtest.helper.AdapterHelper.groupsDTO;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
@TestOffline
public class GroupRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_groups_links() throws Exception {

        given(groupAdapter.groups("cluster1")).willReturn(groupsDTO());

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        final Object document =  parseJson(mvc, BASE_PATH + "/clusterview/se/central/cluster1");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   REL_COUNTRY,
                                                   REGION_PREFIX + "central",
                                                   CLUSTER_PREFIX + "cluster1",
                                                   GROUP_PREFIX + obfuscatedId,
                                                   GROUP_PREFIX + "create",
                                                   REL_STATS_ACTIVITY,
                                                   REL_STATS_COURSE,
                                                   REL_STATS_HISTORY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_group_links() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        given(groupAdapter.group(obfuscatedId)).willReturn(groupDTO());

        final Object document =  parseJson(mvc, BASE_PATH + "/clusterview/se/central/cluster1/" + obfuscatedId);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   REL_COUNTRY,
                                                   REGION_PREFIX + "central",
                                                   CLUSTER_PREFIX + "cluster1",
                                                   GROUP_PREFIX + "update",
                                                   GROUP_PREFIX + "delete",
                                                   REL_STATS_HISTORY);
    }
}