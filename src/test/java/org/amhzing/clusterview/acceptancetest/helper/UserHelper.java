package org.amhzing.clusterview.acceptancetest.helper;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;

public final class UserHelper {

    private UserHelper() {
        // to prevent instantiation
    }

    public static ImmutablePair<String, String> user() {
        return ImmutablePair.of("guest@example.com", "guest123");
    }

    public static ImmutablePair<String, String> adminUser() {
        return ImmutablePair.of("admin@example.com", "admin123");
    }

    public static ImmutablePair<String, String>superAdminUser() {
        return ImmutablePair.of("superadmin@example.com", "superadmin123");
    }

    public static ImmutablePair<String, String> stockholmUser() {
        return ImmutablePair.of("stockholm@example.com", "stockholm123");
    }

    public static void setAuthentication(final String username, final String password) {
        final User user = new User(username, password, emptyList());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
