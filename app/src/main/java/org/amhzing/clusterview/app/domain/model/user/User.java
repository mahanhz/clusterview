package org.amhzing.clusterview.app.domain.model.user;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Name;

import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class User {

    private final Email email;
    private final Password password;
    private final Name name;
    private final State state;
    private final List<Role> roles;
    private final List<Country.Id> countries;

    private User(final Email email, final Password password, final Name name,
                 final State state, final List<Role> roles, final List<Country.Id> countries) {
        this.email = notNull(email);
        this.password = notNull(password);
        this.name = notNull(name);
        this.state = notNull(state);
        this.roles = notNull(roles);
        this.countries = notNull(countries);
    }

    public static User create(final Email email, final Password password,
                              final Name name, final State state,
                              final List<Role> roles, final List<Country.Id> countries) {
        return new User(email, password, name, state, roles, countries);
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Name getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public List<Role> getRoles() {
        return ImmutableList.copyOf(roles);
    }

    public List<Country.Id> getCountries() {
        return ImmutableList.copyOf(countries);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(state, user.state) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(countries, user.countries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, state, roles, countries);
    }

    @Override
    public String toString() {
        return "User{" +
                "email=" + email +
                ", name=" + name +
                ", state=" + state +
                ", roles=" + roles +
                ", countries=" + countries +
                '}';
    }
}
