package org.amhzing.clusterview.user;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

public class UserServletFilter implements Filter {

    private final String APP_USER = "appuser";

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        boolean successfulRegistration = false;

        final HttpServletRequest req = (HttpServletRequest) request;
        final Principal principal = req.getUserPrincipal();

        if (principal != null) {
            String username = principal.getName();
            successfulRegistration = registerUsername(username);
        }

        try {
            chain.doFilter(request, response);
        } finally {
            if (successfulRegistration) {
                MDC.remove(APP_USER);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean registerUsername(final String username) {
        if (StringUtils.isNotBlank(username)) {
            MDC.put(APP_USER, username);
            return true;
        }
        return false;
    }
}
