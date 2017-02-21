package org.amhzing.clusterview.backend.web.controller.appnav;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.backend.annotation.TestOffline;
import org.amhzing.clusterview.backend.security.WithMockCustomUser;
import org.amhzing.clusterview.backend.web.adapter.GroupAdapter;
import org.amhzing.clusterview.backend.web.adapter.StatisticAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.backend.helper.ClientModelHelper.anotherActivityModel;
import static org.amhzing.clusterview.backend.helper.ClientModelHelper.groupModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
@TestOffline
@Ignore

public class GroupControllerTest {

    public static final String OBFUSCATED_GROUP_ID = "3o97MmbN";

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
        given(groupAdapter.group(OBFUSCATED_GROUP_ID)).willReturn(groupModel());

        final ResultActions result = this.mvc.perform(get("/clusterview/se/central/stockholm/" + OBFUSCATED_GROUP_ID))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Element groupId = doc.getElementById("obfuscatedGroupId");

        assertThat(groupId.val()).isEqualToIgnoringCase(OBFUSCATED_GROUP_ID);
    }
}