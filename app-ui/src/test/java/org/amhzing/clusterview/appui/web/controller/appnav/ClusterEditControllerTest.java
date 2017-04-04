package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.appui.annotation.TestOffline;
import org.amhzing.clusterview.appui.security.WithMockCustomUser;
import org.amhzing.clusterview.appui.web.adapter.StatisticAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import static org.amhzing.clusterview.appui.web.controller.appnav.ClusterEditController.COURSE_STATS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClusterEditController.class)
@TestOffline
public class ClusterEditControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @MockBean
    private CommonModelController commonModelController;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_show_course_stats() throws Exception {

        final ResultActions result = this.mvc.perform(get("/clusteredit/se/central/stockholm" + COURSE_STATS))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements form = doc.getElementsByTag("form");

        final String action = form.last().attr("action");
        assertThat(action).endsWith(COURSE_STATS);
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_save_course_stats() throws Exception {
        final ResultActions result = this.mvc.perform(post("/clusteredit/se/central/stockholm" + COURSE_STATS)
                                                              .param("courseStatistics[0].id", "1")
                                                              .param("courseStatistics[0].name", "Book 1")
                                                              .param("courseStatistics[0].quantity", "100")
                                                              .param("courseStatistics[1].id", "2")
                                                              .param("courseStatistics[1].name", "Book 2")
                                                              .param("courseStatistics[1].quantity", "85"))
                                             .andExpect(status().is3xxRedirection());

        final ModelAndView modelAndView = result.andReturn().getModelAndView();

        assertThat(modelAndView.getViewName()).isEqualTo("redirect:/clusterview/se/central/stockholm");
    }
}