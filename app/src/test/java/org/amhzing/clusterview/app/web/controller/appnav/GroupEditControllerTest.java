package org.amhzing.clusterview.app.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.helper.ClientModelHelper;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.amhzing.clusterview.backend.exception.NotFoundException;
import org.amhzing.clusterview.backend.web.adapter.GroupAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
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

import static org.amhzing.clusterview.app.web.controller.exception.GlobalExceptionHandlerController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupEditController.class)
@TestOffline
@Ignore

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
        given(groupAdapter.groups(any())).willReturn(ImmutableSet.of(ClientModelHelper.groupModel()));

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
        Mockito.doThrow(new NotFoundException("Cluster not found")).when(groupAdapter).createGroup(any(), any());

        final ResultActions result = this.mvc.perform(post("/clusteredit/se/central/" + cluster + "/creategroup")
                                                              .param("location.coordX", "1")
                                                              .param("location.coordY", "1")
                                                              .param("members[0].name.firstName", "test")
                                                              .param("members[0].name.lastName", "test"))
                                             .andExpect(status().is4xxClientError());

        final ModelAndView mav = result.andReturn().getModelAndView();

        assertThat(mav.getViewName()).isEqualTo(ERROR_VIEW);
        assertThat(mav.getModel().keySet()).contains(CUSTOM_MESSAGE_KEY);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_be_able_to_edit_group() throws Exception {
        given(groupAdapter.group(OBFUSCATED_GROUP_ID)).willReturn(ClientModelHelper.groupModel());

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
        Mockito.doThrow(new NotFoundException("Group not found")).when(groupAdapter).updateGroup(any(), any());

        final ResultActions result = this.mvc.perform(put("/clusteredit/se/central/stockholm/" + groupId)
                                                              .param("location.coordX", "1")
                                                              .param("location.coordY", "1")
                                                              .param("members[0].name.firstName", "test")
                                                              .param("members[0].name.lastName", "test"))
                                             .andExpect(status().is4xxClientError());

        final ModelAndView mav = result.andReturn().getModelAndView();

        assertThat(mav.getViewName()).isEqualTo(ERROR_VIEW);
        assertThat(mav.getModel().keySet()).contains(CUSTOM_MESSAGE_KEY);
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