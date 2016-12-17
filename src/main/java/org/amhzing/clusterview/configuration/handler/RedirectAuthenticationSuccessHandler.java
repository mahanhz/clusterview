package org.amhzing.clusterview.configuration.handler;

import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.user.DefaultUserDetails;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

import static java.util.stream.Collectors.toList;
import static org.amhzing.clusterview.user.UserRole.USER;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.replace;

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
        final List<String> roles = authentication.getAuthorities()
                                                 .stream()
                                                 .map(GrantedAuthority::getAuthority)
                                                 .collect(toList());

        if (roles.contains(USER.getRole())) {
            final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
            final List<Country.Id> countries = userDetails.getCountries();

            if (CollectionUtils.isNotEmpty(countries)) {
                final Country.Id countryId = countries.iterator().next();
                return URI + countryId.getId();
            }
        }

        return "/error";
    }

    private boolean useSavedUri(final SavedRequest savedRequest, final HttpServletRequest request) {
        if (savedRequest == null) {
            return false;
        } else if (savedRequest instanceof DefaultSavedRequest) {
            final String strippedSavedRequestURI = replace(((DefaultSavedRequest) savedRequest).getRequestURI(), "/", "");
            final String strippedRequestContextPath = replace(request.getContextPath(), "/", "");

            if (strippedSavedRequestURI.equalsIgnoreCase(strippedRequestContextPath)) {
                return false;
            }
        }

        return true;
    }
}
