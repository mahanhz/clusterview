package org.amhzing.clusterview.web.controller.appnav;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.amhzing.clusterview.adapter.web.GroupAdapter;
import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.adapter.web.util.GroupDtoFactory;
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

import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.GroupEditRestController.CREATE_GROUP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupEditRestController.class)
@TestOffline
public class GroupEditRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_create_group() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final GroupDTO groupDto = GroupDtoFactory.convertGroup(group());

        final String json = mapper.writeValueAsString(groupDto);

        final String urlSegment = "/se/central/cluster1";
        final ResultActions result = mvc.perform(post(BASE_PATH + "/clusteredit" + urlSegment + "/" + CREATE_GROUP)
                                                         .contentType(MediaTypes.APPLICATION_JSON_V1)
                                                         .content(json))
                                        .andExpect(status().isCreated());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(BASE_PATH + "/clusterview" + urlSegment);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_update_group() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();
        final GroupDTO groupDto = GroupDtoFactory.convertGroup(group());

        final String json = mapper.writeValueAsString(groupDto);

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        final String urlSegment = "/se/central/cluster1";
        final ResultActions result = mvc.perform(put(BASE_PATH + "/clusteredit" + urlSegment + "/" + obfuscatedId)
                                                         .contentType(MediaTypes.APPLICATION_JSON_V1)
                                                         .content(json))
                                        .andExpect(status().isOk());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(BASE_PATH + "/clusterview" + urlSegment);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_delete_group() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(group().getId().getId());

        final String urlSegment = "/se/central/cluster1";
        final ResultActions result = mvc.perform(delete(BASE_PATH + "/clusteredit" + urlSegment + "/" + obfuscatedId))
                                        .andExpect(status().isOk());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(BASE_PATH + "/clusterview" + urlSegment);
    }
}