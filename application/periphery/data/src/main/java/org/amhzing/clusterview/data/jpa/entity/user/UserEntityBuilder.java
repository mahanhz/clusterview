package org.amhzing.clusterview.data.jpa.entity.user;

import org.amhzing.clusterview.data.jpa.entity.CountryEntity;

import java.util.Set;

public class UserEntityBuilder {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private Set<RoleEntity> roles;
    private Set<CountryEntity> countries;

    public UserEntityBuilder setId(final long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntityBuilder setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntityBuilder setEmail(final String email) {
        this.email = email;
        return this;
    }

    public UserEntityBuilder setPassword(final String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder setEnabled(final boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public UserEntityBuilder setRoles(final Set<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntityBuilder setCountries(final Set<CountryEntity> countries) {
        this.countries = countries;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(id, firstName, lastName, email, password, enabled, roles, countries);
    }
}