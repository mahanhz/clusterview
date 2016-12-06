package org.amhzing.clusterview.configuration.handler;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collection;

import static org.amhzing.clusterview.configuration.handler.RedirectAuthenticationSuccessHandler.SE_URI;
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

        handler.onAuthenticationSuccess(request, response, authentication);

        assertThat(handler.uri(authentication)).isEqualTo(SE_URI);
    }

    private Collection role() {
        return ImmutableSet.of(new SimpleGrantedAuthority(UserRole.SE_USER.getRole()));
    }
}