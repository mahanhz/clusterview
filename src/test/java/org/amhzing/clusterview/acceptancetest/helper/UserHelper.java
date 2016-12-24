package org.amhzing.clusterview.acceptancetest.helper;


import org.apache.commons.lang3.tuple.ImmutablePair;

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
}
