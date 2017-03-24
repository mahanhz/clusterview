package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.amhzing.clusterview.app.web.MediaTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.REL_STATS_ACTIVITY;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.REL_STATS_COURSE;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.REL_STATS_HISTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryRestController.class)
@TestOffline
public class CountryRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_country() throws Exception {

        final ResultActions result = mvc.perform(get(BASE_PATH + "/clusterview/se"))
                                        .andExpect(status().isOk())
                                        .andExpect(content().contentTypeCompatibleWith(MediaTypes.APPLICATION_JSON_V1));

        final String content = result.andReturn().getResponse().getContentAsString();
        final Object document = Configuration.defaultConfiguration().jsonProvider().parse(content);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder("home", "self",
                                                   "region-central", "region-northern", "region-southern",
                                                   REL_STATS_ACTIVITY, REL_STATS_COURSE, REL_STATS_HISTORY);
    }
}