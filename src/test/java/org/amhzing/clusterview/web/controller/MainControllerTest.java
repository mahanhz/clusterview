package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
@TestOffline
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @Test
    public void should_get_country() throws Exception {
        final ResultActions result = this.mvc.perform(get("/clusterview/se"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Element country = doc.getElementById("country");

        assertThat(country.val()).isEqualToIgnoringCase("se");
    }
}