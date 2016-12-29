package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.security.WithMockCustomUser;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.helper.ClientModelHelper.anotherActivityModel;
import static org.amhzing.clusterview.helper.ClientModelHelper.groupModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
@TestOffline
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @MockBean
    private CommonModelController commonModelController;
    
    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_groups() throws Exception {
        given(groupAdapter.groups(any())).willReturn(ImmutableSet.of(groupModel()));

        final ResultActions result = this.mvc.perform(get("/clusterview/se/central/stockholm"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final String imgSrc = doc.getElementsByTag("img").first().attr("src");

        assertThat(imgSrc).contains("cluster-stockholm");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_highlight_groups() throws Exception {
        given(groupAdapter.groups(any())).willReturn(ImmutableSet.of(groupModel()));

        final ResultActions result = this.mvc.perform(get("/clusterview/se/central/stockholm")
                                                              .param("highlight", anotherActivityModel().getName()))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements icons = doc.getElementsByTag("i");

        assertThat(icons.hasClass("group-highlight")).isTrue();
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_group() throws Exception {
        given(groupAdapter.group(1L)).willReturn(groupModel());

        final ResultActions result = this.mvc.perform(get("/clusterview/se/central/stockholm/1"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Element groupId = doc.getElementById("groupId");

        assertThat(groupId.val()).isEqualToIgnoringCase("1");
    }
}