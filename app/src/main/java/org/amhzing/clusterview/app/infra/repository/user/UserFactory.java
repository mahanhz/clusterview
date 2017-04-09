package org.amhzing.clusterview.app.infra.repository.user;

import org.amhzing.clusterview.app.domain.model.*;
import org.amhzing.clusterview.app.domain.model.user.*;
import org.amhzing.clusterview.app.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.user.RoleEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.user.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserFactory {

    private UserFactory() {
        // To prevent instantiation
    }

    public static List<User> users(final List<UserEntity> users) {
        return users.stream()
                    .map(userEntity -> user(email(userEntity.getEmail()),
                                            maskedPassword(),
                                            name(userEntity.getFirstName(), userEntity.getLastName()),
                                            state(userEntity.isEnabled()),
                                            roles(userEntity.getRoles()),
                                            countries(userEntity.getCountries())))
                    .collect(Collectors.toList());
    }

    private static User user(final Email email,
                             final Password password,
                             final Name name,
                             final State state,
                             final List<Role> roles,
                             final List<Country.Id> countries) {
        return User.create(email, password, name, state, roles, countries);
    }

    private static Email email(final String email) {
        return ImmutableEmail.of(email);
    }

    private static Password maskedPassword() {
        return ImmutablePassword.of("********");
    }

    private static Name name(final String firstName, final String lastName) {
        return ImmutableName.builder().firstName(ImmutableFirstName.of(firstName))
                            .lastName(ImmutableLastName.of(lastName))
                            .build();
    }

    private static State state(final boolean active) {
        return ImmutableState.of(active);
    }

    private static List<Role> roles(final Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
                           .map(roleEntity -> role(roleEntity.getName()))
                           .collect(Collectors.toList());
    }

    private static Role role(final String role) {
        return ImmutableRole.of(role);
    }

    private static List<Country.Id> countries(final Set<CountryEntity> countryEntities) {
        return countryEntities.stream()
                              .map(countryEntity -> country(countryEntity.getId()))
                              .collect(Collectors.toList());
    }

    private static Country.Id country(final String country) {
        return Country.Id.create(country);
    }
}
