package org.amhzing.clusterview.infra.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.infra.user.DefaultUserDetails;
import org.amhzing.clusterview.infra.user.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public final class AuthenticationHelper {

    public static final String AUTHENTICATION_COUNTRY = "se";

    private AuthenticationHelper() {
        // To prevent instantiation
    }

    public static DefaultUserDetails userDetails() {
        return new DefaultUserDetails("test", "p", true, true, true, true,
                                      role(),
                                      "First",
                                      "Last",
                                      countries());
    }

    public static Collection role() {
        return ImmutableSet.of(new SimpleGrantedAuthority(UserRole.USER.getRole()),
                               new SimpleGrantedAuthority("ROLE_ADMIN_SE_STOCKHOLM"));
    }

    public static List<String> countries() {
        return ImmutableList.of(AUTHENTICATION_COUNTRY);
    }
}
