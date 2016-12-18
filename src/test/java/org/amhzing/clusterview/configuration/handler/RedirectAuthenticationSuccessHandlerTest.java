package org.amhzing.clusterview.configuration.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.amhzing.clusterview.configuration.handler.RedirectAuthenticationSuccessHandler.URI;
import static org.amhzing.clusterview.helper.AuthenticationHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RedirectAuthenticationSuccessHandlerTest {

    private final RedirectAuthenticationSuccessHandler handler = new RedirectAuthenticationSuccessHandler();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private Authentication authentication;

    @Test
    public void should_use_role_based_uri() throws Exception {
        given(authentication.getAuthorities()).willReturn(role());
        given(authentication.getPrincipal()).willReturn(userDetails());

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(handler.uri(authentication)).isEqualTo(URI + AUTHENTICATION_COUNTRY);
    }
}