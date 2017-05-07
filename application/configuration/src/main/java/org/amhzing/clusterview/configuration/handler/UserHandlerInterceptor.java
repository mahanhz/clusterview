package org.amhzing.clusterview.configuration.handler;

import org.amhzing.clusterview.configuration.user.UserUtil;
import org.amhzing.clusterview.data.repository.user.DefaultUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.amhzing.clusterview.configuration.user.UserUtil.USER_COUNTRY;


public class UserHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (!isAnonymous()) {
            final DefaultUserDetails userDetails = (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            setUserCountry(request, userDetails);
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private void setUserCountry(final HttpServletRequest request,
                                final DefaultUserDetails userDetails) {
        final List<String> countries = userDetails.getCountries();

        if (UserUtil.isSingleCountry(countries)) {
            request.setAttribute(USER_COUNTRY, countries.iterator().next());
        }
    }
}
