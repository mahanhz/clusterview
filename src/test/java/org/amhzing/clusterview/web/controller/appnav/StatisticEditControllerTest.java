package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.security.WithMockCustomUser;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.controller.CommonModelController;
import org.amhzing.clusterview.web.controller.appnav.StatisticEditController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.FlashMap;

import static org.amhzing.clusterview.helper.ClientModelHelper.activityStatisticModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticEditController.class)
@TestOffline
public class StatisticEditControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticAdapter statisticAdapter;

    @MockBean
    private CommonModelController commonModelController;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_save_history() throws Exception {
        given(statisticAdapter.countryStats(any())).willReturn(activityStatisticModel());

        final ResultActions result = this.mvc.perform(post("/statsedit/history/se")
                                                              .param("name", "stockholm"))
                                             .andExpect(status().is3xxRedirection());

        final FlashMap flashMap = result.andReturn().getFlashMap();

        assertThat(flashMap.size()).isEqualTo(1);
        assertThat((String) flashMap.get("statsHistoryMessage")).isEqualToIgnoringCase("stockholm history saved");
    }
}