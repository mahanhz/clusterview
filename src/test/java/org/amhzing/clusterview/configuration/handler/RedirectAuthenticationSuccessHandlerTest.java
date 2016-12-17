package org.amhzing.clusterview.configuration.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.user.DefaultUserDetails;
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
import java.util.List;

import static org.amhzing.clusterview.configuration.handler.RedirectAuthenticationSuccessHandler.URI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RedirectAuthenticationSuccessHandlerTest {

    private static final String COUNTRY = "se";
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

        assertThat(handler.uri(authentication)).isEqualTo(URI + COUNTRY);
    }

    private DefaultUserDetails userDetails() {
        return new DefaultUserDetails("test", "p", true, true, true, true,
                                      role(),
                                      "First",
                                      "Last",
                                      countries());
    }

    private Collection role() {
        return ImmutableSet.of(new SimpleGrantedAuthority(UserRole.USER.getRole()));
    }

    private List<Country.Id> countries() {
        return ImmutableList.of(Country.Id.create(COUNTRY));
    }
}