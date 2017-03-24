package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.application.ClusterService;
import org.amhzing.clusterview.app.application.StatisticHistoryService;
import org.amhzing.clusterview.app.application.StatisticService;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.amhzing.clusterview.app.helper.DomainModelHelper.activityStatistic;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.clustersIds;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.StatisticRestController.HISTORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticEditRestController.class)
@TestOffline
public class StatisticEditRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticHistoryService statisticHistoryService;
    @MockBean
    private StatisticService<ActivityStatistic> statisticService;
    @MockBean
    private ClusterService clusterService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_save_history() throws Exception {

        given(statisticService.statistics(any(Country.Id.class))).willReturn(activityStatistic());
        given(clusterService.clusters(any(Country.Id.class))).willReturn(clustersIds());

        final String urlEnding = HISTORY + "/se/cluster1";
        final ResultActions result = mvc.perform(post(BASE_PATH + "/statsedit" + urlEnding))
                                        .andExpect(status().isCreated());

        final String locationHeader = result.andReturn().getResponse().getHeader("Location");

        assertThat(locationHeader).isNotBlank();
        assertThat(locationHeader).endsWith(BASE_PATH + "/statsview" + urlEnding);
    }
}