package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.amhzing.clusterview.backend.web.adapter.StatisticAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
@TestOffline
@Ignore

public class CountryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @MockBean
    private CommonModelController commonModelController;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_country() throws Exception {

        final ResultActions result = this.mvc.perform(get("/clusterview/se"))
                                             .andExpect(status().isOk());

        final String content = result.andReturn().getResponse().getContentAsString();

        Document doc = Jsoup.parse(content);
        final Elements maps = doc.getElementsByTag("map");

        assertThat(maps).hasSize(1);
        assertThat(maps.first().attr("name")).isEqualTo("regionsMap");
        assertThat(maps.first().children()).isNotEmpty();
    }
}