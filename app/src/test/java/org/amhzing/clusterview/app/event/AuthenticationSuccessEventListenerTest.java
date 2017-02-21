package org.amhzing.clusterview.app.event;

import org.amhzing.clusterview.app.helper.AuthenticationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationSuccessEventListenerTest {

    @Mock
    private AuthenticationSuccessEvent event;

    private AuthenticationSuccessEventListener authenticationSuccessEventListener;
    private HttpSession httpSession;

    @Before
    public void setUp() throws Exception {
        httpSession = new MockHttpSession();
        authenticationSuccessEventListener = new AuthenticationSuccessEventListener(httpSession);
    }

    @Test
    public void should_set_user_country_into_session() throws Exception {
        given(event.getAuthentication()).willReturn(authenticationToken());

        authenticationSuccessEventListener.onApplicationEvent(event);

        assertThat(httpSession.getAttribute("userCountry")).isEqualTo("se");

    }

    private UsernamePasswordAuthenticationToken authenticationToken() {
        return new UsernamePasswordAuthenticationToken(AuthenticationHelper.userDetails(), null);
    }
}