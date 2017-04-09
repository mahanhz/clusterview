package org.amhzing.clusterview.app.web.controller.rest.user;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.app.annotation.TestOffline;
import org.amhzing.clusterview.app.application.UserService;
import org.amhzing.clusterview.app.domain.model.user.Page;
import org.amhzing.clusterview.app.domain.model.user.User;
import org.amhzing.clusterview.app.security.WithMockCustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.amhzing.clusterview.app.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.app.helper.UserDomainModelHelper.users;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.RestControllerPath.MANAGE_PATH;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.REL_HOME;
import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.USERS_PAGE_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.Link.REL_SELF;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
@TestOffline
public class UserRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_users_links() throws Exception {

        final int urlPageNumber = 1;

        given(userService.users(urlPageNumber - 1)).willReturn(pagedUsers());

        final Object document =  parseJson(mvc, MANAGE_PATH + BASE_PATH + "/users/page/" + urlPageNumber);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   USERS_PAGE_PREFIX + 2);
    }

    private Page<User> pagedUsers() {
        return Page.create(users(), 2);
    }
}