package org.amhzing.clusterview.backend.user;

import org.amhzing.clusterview.backend.helper.AuthenticationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class WebSecurityTest {

    @Mock
    private Authentication authentication;

    private WebSecurity webSecurity;

    @Before
    public void setUp() throws Exception {
        webSecurity = new WebSecurity();
    }

    @Test
    public void should_allow_when_user_belongs_to_country() throws Exception {
        given(authentication.getPrincipal()).willReturn(AuthenticationHelper.userDetails());

        final boolean result = webSecurity.checkCountry(authentication, AuthenticationHelper.AUTHENTICATION_COUNTRY);

        assertThat(result).isTrue();
    }

    @Test
    public void should_not_allow_when_user_does_not_belong_to_country() throws Exception {
        given(authentication.getPrincipal()).willReturn(AuthenticationHelper.userDetails());

        final boolean result = webSecurity.checkCountry(authentication, "dk");

        assertThat(result).isFalse();
    }

    @Test
    public void should_allow_when_user_belongs_to_cluster() throws Exception {
        given(authentication.getPrincipal()).willReturn(AuthenticationHelper.userDetails());

        final boolean result = webSecurity.checkAdmin(authentication, "stockholm");

        assertThat(result).isTrue();
    }

    @Test
    public void should_not_allow_when_user_does_not_belong_to_cluster() throws Exception {
        given(authentication.getPrincipal()).willReturn(AuthenticationHelper.userDetails());

        final boolean result = webSecurity.checkAdmin(authentication, "uppsala");

        assertThat(result).isFalse();
    }
}