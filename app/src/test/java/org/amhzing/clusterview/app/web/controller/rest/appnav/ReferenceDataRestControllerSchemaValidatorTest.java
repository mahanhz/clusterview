package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.application.ActivityService;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.CoreActivityService;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.helper.RestHelper;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.amhzing.clusterview.app.web.MediaTypes;
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
import static org.amhzing.clusterview.app.web.controller.rest.entry.IndexRestController.USER_COUNTRY;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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
    private ActivityService activityService;
    @MockBean
    private CoreActivityService coreActivityService;
    @MockBean
    private ClusterService clusterService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_activities_schema() throws Exception {

        given(activityService.activities()).willReturn(ImmutableList.of(activity()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/referencedata/activities");

        assertSuccessfulSchemaValidation(result, "ReferenceActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_core_activities_schema() throws Exception {

        given(coreActivityService.coreActivities()).willReturn(ImmutableList.of(coreActivity()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/referencedata/coreactivities");

        assertSuccessfulSchemaValidation(result, "ReferenceActivitiesDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_validate_clusters_schema() throws Exception {

        given(clusterService.clusters(any(Country.Id.class))).willReturn(clustersIds());

        final ResultActions result = mvc.perform(get(BASE_PATH + "/referencedata/clusters").sessionAttr(USER_COUNTRY, "se"))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_JSON_V1));

        assertSuccessfulSchemaValidation(result, "ClustersDTO.json");
    }
}