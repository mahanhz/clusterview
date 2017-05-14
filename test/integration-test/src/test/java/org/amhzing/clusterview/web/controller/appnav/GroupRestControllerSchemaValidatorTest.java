package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.GroupAdapter;
import org.amhzing.clusterview.adapter.web.Obfuscator;
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

import static org.amhzing.clusterview.integrationtest.helper.AdapterHelper.groupDTO;
import static org.amhzing.clusterview.integrationtest.helper.AdapterHelper.groupsDTO;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
@TestOffline
public class GroupRestControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_groups_links() throws Exception {

        given(groupAdapter.groups("cluster1")).willReturn(groupsDTO());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/clusterview/se/central/cluster1");

        assertSuccessfulSchemaValidation(result, "GroupsDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_group_links() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        given(groupAdapter.group(obfuscatedId)).willReturn(groupDTO());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/clusterview/se/central/cluster1/" + obfuscatedId);

        assertSuccessfulSchemaValidation(result, "GroupDTO.json");
    }
}