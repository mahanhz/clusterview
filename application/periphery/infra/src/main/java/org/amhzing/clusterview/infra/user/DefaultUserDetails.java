package org.amhzing.clusterview.infra.user;

import com.google.common.collect.ImmutableList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

public class DefaultUserDetails extends User {

    private String firstName;
    private String lastName;
    private List<String> countries;

    public DefaultUserDetails(final String username,
                              final String password,
                              final boolean enabled,
                              final boolean accountNonExpired,
                              final boolean credentialsNonExpired,
                              final boolean accountNonLocked,
                              final Collection<? extends GrantedAuthority> authorities,
                              final String firstName,
                              final String lastName,
                              final List<String> countries) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.firstName = notBlank(firstName);
        this.lastName = notBlank(lastName);
        this.countries = notEmpty(countries);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getCountries() {
        return ImmutableList.copyOf(countries);
    }

    @Override
    public String toString() {
        return "DefaultUserDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", countries=" + countries +
                "} " + super.toString();
    }
}
