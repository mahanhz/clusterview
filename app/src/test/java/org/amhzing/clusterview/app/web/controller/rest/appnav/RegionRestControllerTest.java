package org.amhzing.clusterview.app.web.controller.rest.appnav;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.amhzing.clusterview.app.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(RegionRestController.class)
@TestOffline
public class RegionRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_links() throws Exception {

        final Object document =  parseJson(mvc, BASE_PATH + "/clusterview/se/central");

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF, REL_HOME, REL_COUNTRY,
                                                   REL_STATS_ACTIVITY, REL_STATS_COURSE, REL_STATS_HISTORY);
    }
}