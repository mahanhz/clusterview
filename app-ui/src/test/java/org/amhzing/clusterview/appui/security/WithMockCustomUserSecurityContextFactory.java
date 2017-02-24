package org.amhzing.clusterview.appui.security;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.user.DefaultUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    public SecurityContext createSecurityContext(WithMockCustomUser withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser
                .username() : withUser.value();
        if (username == null) {
            throw new IllegalArgumentException(withUser
                                                       + " cannot have null username on both username and value properites");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (String authority : withUser.authorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        if(grantedAuthorities.isEmpty()) {
            for (String role : withUser.roles()) {
                if (role.startsWith("ROLE_")) {
                    throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
                                                               + role);
                }
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        } else if(!(withUser.roles().length == 1 && "USER".equals(withUser.roles()[0]))) {
            throw new IllegalStateException("You cannot define roles attribute "+ Arrays.asList(withUser.roles())+" with authorities attribute "+ Arrays.asList(withUser.authorities()));
        }

        DefaultUserDetails principal = new DefaultUserDetails(username, withUser.password(), true, true, true, true,
                                                              grantedAuthorities,
                                                              withUser.firstName(),
                                                              withUser.lastName(),
                                                              countries());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }

    private List<String> countries() {
        return ImmutableList.of("se");
    }
}
