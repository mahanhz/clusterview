package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.ActivityAdapter;
import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.helper.RestHelper;
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
import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.util.UserUtil.USER_COUNTRY;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceDataRestController.class)
@TestOffline
public class ReferenceDataRestControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ActivityAdapter activityAdapter;
    @MockBean
    private ClusterAdapter clusterAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activities_schema() throws Exception {

        given(activityAdapter.activities()).willReturn(activitiesDTO(ImmutableList.of(activity())));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/referencedata/activities");

        assertSuccessfulSchemaValidation(result, "ReferenceActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_core_activities_schema() throws Exception {

        given(activityAdapter.coreActivities()).willReturn(coreActivitiesDTO(ImmutableList.of(coreActivity())));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/referencedata/coreactivities");

        assertSuccessfulSchemaValidation(result, "ReferenceActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_clusters_schema() throws Exception {

        given(clusterAdapter.clusters(COUNTRY)).willReturn(clustersDTO(clustersIds()));

        final ResultActions result = mvc.perform(get(BASE_PATH + "/referencedata/clusters").requestAttr(USER_COUNTRY, COUNTRY))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_JSON_V1));

        assertSuccessfulSchemaValidation(result, "ClustersDTO.json");
    }
}