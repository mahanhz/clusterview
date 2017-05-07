package org.amhzing.clusterview.configuration.handler;

import org.amhzing.clusterview.configuration.helper.AuthenticationHelper;
import org.amhzing.clusterview.data.repository.user.DefaultUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.amhzing.clusterview.configuration.user.UserUtil.USER_COUNTRY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class UserHandlerInterceptorTest {

    private UserHandlerInterceptor userHandlerInterceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userHandlerInterceptor = new UserHandlerInterceptor();
    }

    @Test
    public void should_set_user_country_for_logged_in_user() throws Exception {
        givenLoggedInUser();

        userHandlerInterceptor.preHandle(request, response, null);

        assertThat(request.getAttribute(USER_COUNTRY)).isEqualTo("se");
    }

    @Test
    public void should_not_set_user_country_for_anonymous() throws Exception {
        givenAnonymousUser();

        userHandlerInterceptor.preHandle(request, response, null);

        assertThat(request.getAttribute(USER_COUNTRY)).isNull();
    }

    private void givenLoggedInUser() {
        final DefaultUserDetails user = AuthenticationHelper.userDetails();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void givenAnonymousUser() {
        final Authentication auth = new AnonymousAuthenticationToken("anon",
                                                                     "ymous",
                                                                     createAuthorityList("ROLE_ONE"));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}