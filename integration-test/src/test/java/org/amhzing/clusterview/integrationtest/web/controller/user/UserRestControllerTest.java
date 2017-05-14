package org.amhzing.clusterview.integrationtest.web.controller.user;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.amhzing.clusterview.adapter.web.UserAdapter;
import org.amhzing.clusterview.adapter.web.util.UserDtoFactory;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.User;
import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.integrationtest.security.WithMockCustomUser;
import org.amhzing.clusterview.web.controller.user.UserRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.amhzing.clusterview.integrationtest.helper.RestHelper.parseJson;
import static org.amhzing.clusterview.integrationtest.helper.UserDomainModelHelper.users;
import static org.amhzing.clusterview.web.controller.RestControllerPath.BASE_PATH;
import static org.amhzing.clusterview.web.controller.RestControllerPath.MANAGE_PATH;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.REL_HOME;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.USERS_PAGE_PREFIX;
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
    private UserAdapter userAdapter;

    @Test
    @WithMockCustomUser(username = "testU", password = "NotSaying")
    public void should_get_users_links() throws Exception {

        final int urlPageNumber = 1;

        given(userAdapter.users(urlPageNumber)).willReturn(UserDtoFactory.users((pagedUsers().getContent())));
        given(userAdapter.pagedUsers(urlPageNumber)).willReturn(pagedUsers());

        final Object document = parseJson(mvc, MANAGE_PATH + BASE_PATH + "/users/page/" + urlPageNumber);

        final JSONArray rels = JsonPath.read(document, "$.links..rel");

        assertThat(rels).containsExactlyInAnyOrder(REL_SELF,
                                                   REL_HOME,
                                                   USERS_PAGE_PREFIX + 2);
    }

    private Page<User> pagedUsers() {
        return Page.create(users(), 2);
    }
}