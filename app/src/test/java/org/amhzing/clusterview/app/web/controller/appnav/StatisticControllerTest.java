package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
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
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticController.class)
@TestOffline
@Ignore

public class StatisticControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommonModelController commonModelController;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_clusters() throws Exception {
        final ResultActions result = this.mvc.perform(get("/statsview/history/se"))
                                             .andExpect(status().isOk());

        final ModelAndView modelAndView = result.andReturn().getModelAndView();
        assertThat(modelAndView.getModel()).containsKey(CommonModelController.CLUSTER_VALUES_MODEL);

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Element clustersSelect = doc.getElementById("name");

        assertThat(clustersSelect.firstElementSibling().text()).isEqualToIgnoringCase("None selected");
    }

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_cluster_history() throws Exception {
        final ResultActions result = this.mvc.perform(get("/statsview/history/se/stockholm"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements highchartTables = doc.getElementsByClass("highchart");

        assertThat(highchartTables).hasSize(4);
    }
}