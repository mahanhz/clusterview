package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.Obfuscator;
import org.amhzing.clusterview.boundary.enter.GroupService;
import org.amhzing.clusterview.controller.appnav.GroupRestController;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;
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

import static org.amhzing.clusterview.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.integrationtest.helper.SchemaValidationHelper.assertSuccessfulSchemaValidation;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupRestController.class)
@TestOffline
public class GroupRestControllerSchemaValidatorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupService groupService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_groups_links() throws Exception {

        given(groupService.groups(any(Cluster.Id.class))).willReturn(ImmutableSet.of(group()));

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/clusterview/se/central/cluster1");

        assertSuccessfulSchemaValidation(result, "GroupsDTO.json");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_group_links() throws Exception {

        given(groupService.group(any(Group.Id.class))).willReturn(group());

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        final ResultActions result = RestHelper.get(mvc, BASE_PATH + "/clusterview/se/central/cluster1/" + obfuscatedId);

        assertSuccessfulSchemaValidation(result, "GroupDTO.json");
    }
}