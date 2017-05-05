package org.amhzing.clusterview.helper;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.user.*;

import java.util.List;

import static org.amhzing.clusterview.helper.DomainModelHelper.name;

public final class UserDomainModelHelper {

    private UserDomainModelHelper() {
        // To prevent instantiation
    }

    public static List<User> users() {
        return ImmutableList.of(user("john@example.com"),
                                user("laura@example.com"),
                                user("brian@example.com"));
    }

    public static User user(final String email) {
        return User.create(email(email), maskedPassword(), name(), state(), roles(), countries());
    }

    public static Email email(final String email) {
        return ImmutableEmail.of(email);
    }

    public static Email email() {
        return email("test@example.com");
    }

    public static Password maskedPassword() {
        return ImmutablePassword.of("********");
    }

    public static State state() {
        return ImmutableState.of(true);
    }

    public static List<Role> roles() {
        return ImmutableList.of(ImmutableRole.of("ADMIN"));
    }

    public static List<Country.Id> countries() {
        return ImmutableList.of(Country.Id.create("se"));
    }
}
