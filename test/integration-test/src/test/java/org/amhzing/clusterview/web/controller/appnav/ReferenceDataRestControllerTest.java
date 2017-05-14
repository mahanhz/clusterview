package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.ActivityAdapter;
import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.MediaTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.adapter.web.util.ActivityDtoFactory.activitiesDTO;
import static org.amhzing.clusterview.adapter.web.util.ActivityDtoFactory.coreActivitiesDTO;
import static org.amhzing.clusterview.adapter.web.util.ClusterDtoFactory.clustersDTO;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.*;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.COUNTRY;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.REL_HOME;
import static org.amhzing.clusterview.web.controller.util.UserUtil.USER_COUNTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceDataRestController.class)
@TestOffline
public class ReferenceDataRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ActivityAdapter activityAdapter;
    @MockBean
    private ClusterAdapter clusterAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_activities_links() throws Exception {

        given(activityAdapter.activities()).willReturn(activitiesDTO(ImmutableList.of(activity())));

        final Object document = parseJson(mvc, BASE_PATH + "/referencedata/activities");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_core_activities_links() throws Exception {

        given(activityAdapter.coreActivities()).willReturn(coreActivitiesDTO(ImmutableList.of(coreActivity())));

        final Object document =  parseJson(mvc, BASE_PATH + "/referencedata/coreactivities");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_clusters_links() throws Exception {

        given(clusterAdapter.clusters(COUNTRY)).willReturn(clustersDTO(clustersIds()));

        final ResultActions result = mvc.perform(get(BASE_PATH + "/referencedata/clusters").requestAttr(USER_COUNTRY, COUNTRY))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME);
    }
}