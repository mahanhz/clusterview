package org.amhzing.clusterview.integrationtest.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.ClusterAdapter;
import org.amhzing.clusterview.adapter.web.StatisticAdapter;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.controller.appnav.StatisticEditRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.adapter.web.util.ClusterDtoFactory.clustersDTO;
import static org.amhzing.clusterview.integrationtest.helper.DomainModelHelper.clustersIds;
import static org.amhzing.clusterview.integrationtest.helper.RestHelper.COUNTRY;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.StatisticRestController.HISTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticEditRestController.class)
@TestOffline
public class StatisticEditRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;
    @MockBean
    private ClusterAdapter clusterAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_save_history() throws Exception {

        given(clusterAdapter.clusters(COUNTRY)).willReturn(clustersDTO(clustersIds()));

        final String urlEnding = HISTORY + "/se/cluster1";
        final ResultActions result = mvc.perform(post(BASE_PATH + "/statsedit" + urlEnding))
                                        .andExpect(status().isCreated());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(BASE_PATH + "/statsview" + urlEnding);
    }
}