package org.amhzing.clusterview.configuration.handler;

import org.amhzing.clusterview.configuration.user.UserRole;
import org.amhzing.clusterview.configuration.user.UserUtil;
import org.amhzing.clusterview.data.repository.user.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

public class RedirectAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String URI = "/clusterview/";

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUri = "";

        final String roleBasedTargetUri = uri(authentication);

        if (savedRequest == null && isBlank(roleBasedTargetUri)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);

        targetUri = useSavedUri(savedRequest, request) ? savedRequest.getRedirectUrl() : roleBasedTargetUri;
        getRedirectStrategy().sendRedirect(request, response, targetUri);
    }

    protected String uri(final Authentication authentication) {
        final List<String> roles = UserUtil.roles(authentication);

        if (roles.contains(UserRole.USER.getRole())) {
            final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
            final List<String> countries = userDetails.getCountries();

            if (UserUtil.isSingleCountry(countries)) {
                final String countryId = countries.iterator().next();
                return URI + countryId;
            } else {
                // TODO - should maybe redirect to a page where the user can choose which country from a list
            }
        }

        return "/error";
    }

    private boolean useSavedUri(final SavedRequest savedRequest, final HttpServletRequest request) {
        if (savedRequest == null) {
            return false;
        } else if (savedRequest instanceof DefaultSavedRequest) {
            final String savedRequestURI = ((DefaultSavedRequest) savedRequest).getRequestURI();
            final String strippedSavedRequestURI = replace(savedRequestURI, "/", "");
            final String strippedRequestContextPath = replace(request.getContextPath(), "/", "");

            if (startsWithIgnoreCase(savedRequestURI, "/stagemonitor")
                    || strippedSavedRequestURI.equalsIgnoreCase(strippedRequestContextPath)) {
                return false;
            }
        }

        return true;
    }
}
