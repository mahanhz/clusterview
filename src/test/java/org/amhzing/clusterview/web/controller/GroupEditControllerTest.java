package org.amhzing.clusterview.web.controller;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.cache.CacheEvicter;
import org.amhzing.clusterview.security.WithMockCustomUser;
import org.amhzing.clusterview.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
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

import static org.amhzing.clusterview.helper.ClientModelHelper.groupModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupEditController.class)
@TestOffline
public class GroupEditControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupAdapter groupAdapter;

    @MockBean
    private ActivityAdapter activityAdapter;

    @MockBean
    private CoreActivityAdapter coreActivityAdapter;

    @MockBean
    private CacheEvicter cacheEvicter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_be_able_to_create_new_group() throws Exception {
        given(groupAdapter.groups(any())).willReturn(ImmutableSet.of(groupModel()));

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm/newgroup"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements forms = doc.getElementsByTag("form");

        assertThat(forms).hasSize(1);
        final Element form = forms.get(0);

        assertThat(form.attr("action")).isEqualTo("creategroup");
        assertThat(form.attr("method")).isEqualToIgnoringCase("post");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void sshould_be_able_to_edit_new_group() throws Exception {
        given(groupAdapter.group(1L)).willReturn(groupModel());

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm/1"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements forms = doc.getElementsByTag("form");

        assertThat(forms).hasSize(1);
        final Element form = forms.get(0);

        assertThat(form.attr("action")).isEqualTo("1");
        assertThat(form.attr("method")).isEqualToIgnoringCase("post");

        final Elements hiddenMethodElements = form.getElementsByAttributeValueMatching("name", "_method");
        assertThat(hiddenMethodElements).hasSize(1);
        assertThat(hiddenMethodElements.get(0).attr("value")).isEqualToIgnoringCase("put");
    }
}