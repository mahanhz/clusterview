package org.amhzing.clusterview.data.repository.user;

import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtil {

    private UserUtil() {
        // to prevent instantiation
    }

    public static String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
