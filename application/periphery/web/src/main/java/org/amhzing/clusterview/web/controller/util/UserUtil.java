package org.amhzing.clusterview.web.controller.util;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public final class UserUtil {

    private UserUtil() {
        // to prevent instantiation
    }

    public static final String USER_COUNTRY = "userCountry";

    public static List<String> roles(final Authentication authentication) {
        notNull(authentication);

        return authentication.getAuthorities()
                             .stream()
                             .map(GrantedAuthority::getAuthority)
                             .collect(toList());
    }

    public static boolean isSingleCountry(final List<String> countries) {
        return CollectionUtils.isNotEmpty(countries) && countries.size() == 1;
    }

    public static String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isSuperAdmin() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return roles(authentication).contains(UserRole.SUPER_ADMIN.getRole());
        }

        return false;
    }
}
