package org.amhzing.clusterview.backend.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static org.apache.commons.collections.CollectionUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class WebSecurity {

    public boolean checkCountry(final Authentication authentication, final String country) {
        notNull(authentication);
        notBlank(country);

        final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();

        return userDetails.getCountries().contains(country);
    }

    public boolean checkAdmin(final Authentication authentication, final String cluster) {
        notNull(authentication);
        notBlank(cluster);

        final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
        final Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        return userDetails.getCountries()
                          .stream()
                          .anyMatch(country -> isRoleAdmin(authorities, country, cluster));
    }

    private boolean isRoleAdmin(final Collection<GrantedAuthority> authorities, final String country, final String cluster) {
        return containsAny(roles(authorities), of("ROLE_SUPER_ADMIN",
                                                  "ROLE_ADMIN_" + upperCase(country) + "_ALL",
                                                  "ROLE_ADMIN_" + upperCase(country) + "_" + upperCase(cluster)));
    }

    private List<String> roles(final Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                          .map(grantedAuthority -> grantedAuthority.getAuthority())
                          .collect(Collectors.toList());
    }
}
