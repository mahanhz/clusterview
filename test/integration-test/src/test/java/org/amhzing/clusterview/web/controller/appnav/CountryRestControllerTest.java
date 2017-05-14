package org.amhzing.clusterview.web.controller.appnav;

import com.google.common.collect.ImmutableList;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.RegionAdapter;
import org.amhzing.clusterview.adapter.web.api.RegionDTO;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.amhzing.clusterview.integrationtest.helper.RestHelper.COUNTRY;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryRestController.class)
@TestOffline
public class CountryRestControllerTest {

    public static final String REGION_1 = "central";
    public static final String REGION_2 = "northern";
    public static final String REGION_3 = "southern";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionAdapter regionAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_links() throws Exception {

        given(regionAdapter.regions(COUNTRY)).willReturn(regions());

        final Object document = parseJson(mvc, BASE_PATH + "/clusterview/" + COUNTRY);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_HOME, REL_SELF,
                                                   REGION_PREFIX + REGION_1,
                                                   REGION_PREFIX + REGION_2,
                                                   REGION_PREFIX + REGION_3,
                                                   REL_STATS_ACTIVITY, REL_STATS_COURSE, REL_STATS_HISTORY);
    }

    private List<RegionDTO> regions() {
        return ImmutableList.of(region(REGION_1),
                                region(REGION_2),
                                region(REGION_3));
    }

    private RegionDTO region(final String name) {
        return new RegionDTO(name);
    }
}