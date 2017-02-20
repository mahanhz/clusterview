package org.amhzing.clusterview.backend.user;

import org.apache.log4j.MDC;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserServletFilterTest {

    private static final String USERNAME = "user";

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserServletFilter userServletFilter;

    @Before
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userServletFilter = new UserServletFilter();
    }

    @Test
    public void should_set_username_into_mdc() throws Exception {
        request.setUserPrincipal(new UsernamePasswordAuthenticationToken(USERNAME, "ROLE_USER"));

        userServletFilter.doFilter(request, response, new VerifyMDCFilterChain(UserServletFilter.APP_USER, USERNAME));

        // MDC should be cleared afterwards
        assertThat(MDC.get(UserServletFilter.APP_USER)).isNull();
    }

    /**
     * Test helper to verify the content of the MDC once the Filter under test delegates on the next filter.
     */
    private class VerifyMDCFilterChain implements FilterChain {

        private String key;
        private String value;

        public VerifyMDCFilterChain(final String expectedKey, final String expectedValue) {
            super();
            this.key = notBlank(expectedKey);
            this.value = notBlank(expectedValue);
        }

        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response) {
            assertThat(MDC.get(key)).isEqualTo(value);
        }
    }
}