package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.exception.ClusterNotFoundException;
import org.amhzing.clusterview.exception.GroupNotFoundException;
import org.amhzing.clusterview.security.WithMockCustomUser;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import static org.amhzing.clusterview.helper.ClientModelHelper.groupModel;
import static org.amhzing.clusterview.web.controller.exception.GlobalExceptionHandlerController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupEditController.class)
@TestOffline
public class GroupEditControllerTest {

    public static final String OBFUSCATED_GROUP_ID = "3o97MmbN";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @MockBean
    private CommonModelController commonModelController;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_be_able_to_create_new_group() throws Exception {
        given(groupAdapter.groups(any())).willReturn(ImmutableSet.of(groupModel()));

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm/newgroup"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements forms = doc.getElementsByTag("form");

        assertThat(forms).hasSize(2);
        final Element form = forms.get(1);

        assertThat(form.attr("action")).endsWith("creategroup");
        assertThat(form.attr("method")).isEqualToIgnoringCase("post");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_not_be_able_to_create_new_group() throws Exception {
        final String cluster = "fake-cluster";
        Mockito.doThrow(new ClusterNotFoundException("Cluster not found", cluster)).when(groupAdapter).createGroup(any(), any());

        final ResultActions result = this.mvc.perform(post("/clusteredit/se/central/" + cluster + "/creategroup")
                                                              .param("location.coordX", "1")
                                                              .param("location.coordY", "1")
                                                              .param("members[0].name.firstName", "test")
                                                              .param("members[0].name.lastName", "test"))
                                             .andExpect(status().isOk());

        final ModelAndView mav = result.andReturn().getModelAndView();

        assertThat(mav.getViewName()).isEqualTo(ERROR_VIEW);
        assertThat(mav.getModel().keySet()).contains(CUSTOM_MESSAGE_KEY);
        assertThat((String) mav.getModel().get(CUSTOM_MESSAGE_KEY)).contains(cluster);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_be_able_to_edit_group() throws Exception {
        given(groupAdapter.group(OBFUSCATED_GROUP_ID)).willReturn(groupModel());

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm/" + OBFUSCATED_GROUP_ID))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements forms = doc.getElementsByTag("form");

        assertThat(forms).hasSize(2);
        final Element form = forms.get(1);

        assertThat(form.attr("action")).endsWith(OBFUSCATED_GROUP_ID);
        assertThat(form.attr("method")).isEqualToIgnoringCase("post");

        final Elements hiddenMethodElements = form.getElementsByAttributeValueMatching("name", "_method");
        assertThat(hiddenMethodElements).hasSize(1);
        assertThat(hiddenMethodElements.get(0).attr("value")).isEqualToIgnoringCase("put");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_not_be_able_to_update_group() throws Exception {
        final String cluster = "fake-cluster";
        final String groupId = "123";
        Mockito.doThrow(new GroupNotFoundException("Group not found", cluster, groupId)).when(groupAdapter).updateGroup(any(), any());

        final ResultActions result = this.mvc.perform(put("/clusteredit/se/central/stockholm/" + groupId)
                                                              .param("location.coordX", "1")
                                                              .param("location.coordY", "1")
                                                              .param("members[0].name.firstName", "test")
                                                              .param("members[0].name.lastName", "test"))
                                             .andExpect(status().isOk());

        final ModelAndView mav = result.andReturn().getModelAndView();

        assertThat(mav.getViewName()).isEqualTo(ERROR_VIEW);
        assertThat(mav.getModel().keySet()).contains(CUSTOM_MESSAGE_KEY);
        assertThat((String) mav.getModel().get(CUSTOM_MESSAGE_KEY)).contains(cluster);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_catch_unexpected_error() throws Exception {
        given(groupAdapter.group(OBFUSCATED_GROUP_ID)).willThrow(new RuntimeException("Something unexpected happened"));

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm/" + OBFUSCATED_GROUP_ID))
                                             .andExpect(status().isOk());

        final ModelAndView mav = result.andReturn().getModelAndView();

        assertThat(mav.getViewName()).isEqualTo(ERROR_VIEW);
        assertThat(mav.getModel().keySet()).contains(ERROR_ID_KEY);
    }
}