package org.amhzing.clusterview.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static org.apache.commons.lang3.Validate.notBlank;

public class DefaultUserDetails extends User {

    private String firstName;
    private String lastName;

    public DefaultUserDetails(final String username,
                              final String password,
                              final Collection<? extends GrantedAuthority> authorities,
                              final String firstName,
                              final String lastName) {
        super(username, password, authorities);
        this.firstName = notBlank(firstName);
        this.lastName = notBlank(lastName);
    }

    public DefaultUserDetails(final String username,
                              final String password,
                              final boolean enabled,
                              final boolean accountNonExpired,
                              final boolean credentialsNonExpired,
                              final boolean accountNonLocked,
                              final Collection<? extends GrantedAuthority> authorities,
                              final String firstName,
                              final String lastName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "DefaultUserDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "} " + super.toString();
    }
}
