package org.amhzing.clusterview.configuration.user;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class DefaultAuthenticationProviderTest {

    private static final String PASSWORD = "not-saying";

    private DefaultAuthenticationProvider defaultAuthenticationProvider;

    @Before
    public void setUp() throws Exception {
        defaultAuthenticationProvider = new DefaultAuthenticationProvider();
        defaultAuthenticationProvider.setUserDetailsService(new MockUserDetailsService());
    }

    @Test
    public void should_authenticate_successfully() throws Exception {
        final Authentication authentication = defaultAuthenticationProvider.authenticate(authenticationToken("john", PASSWORD));

        assertThat(authentication).isNotNull();
        assertThat(authentication.getCredentials()).isEqualTo(PASSWORD);
    }

    @Test(expected = BadCredentialsException.class)
    public void should_fail_authentication() {
        defaultAuthenticationProvider.authenticate(authenticationToken("hacker", "guessing"));

        fail("Authentication should have failed");
    }

    private UsernamePasswordAuthenticationToken authenticationToken(final String principal, final String credentials) {
        return new UsernamePasswordAuthenticationToken(principal, credentials);
    }

    private class MockUserDetailsService implements UserDetailsService {

        private String password = PASSWORD;

        public UserDetails loadUserByUsername(String username) {
            if ("john".equals(username)) {
                return new User("john", password, true, true, true, true, emptyList());
            }
            else {
                throw new UsernameNotFoundException("Could not find: " + username);
            }
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}