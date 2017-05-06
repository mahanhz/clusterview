package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.Obfuscator;
import org.amhzing.clusterview.boundary.enter.GroupService;
import org.amhzing.clusterview.controller.appnav.GroupRestController;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;
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
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
@TestOffline
public class GroupRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupService groupService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_groups_links() throws Exception {

        given(groupService.groups(any(Cluster.Id.class))).willReturn(ImmutableSet.of(group()));

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

        given(groupService.group(any(Group.Id.class))).willReturn(group());

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

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