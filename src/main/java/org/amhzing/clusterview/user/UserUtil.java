package org.amhzing.clusterview.user;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class UserUtil {

    private UserUtil() {
        // to prevent instantiation
    }

    public static List<String> roles(final Authentication authentication) {
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
}
