package org.amhzing.clusterview.web.controller.util;

import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.user.Role;
import org.amhzing.clusterview.core.domain.user.User;
import org.amhzing.clusterview.web.api.user.CountryDTO;
import org.amhzing.clusterview.web.api.user.RoleDTO;
import org.amhzing.clusterview.web.api.user.UserDTO;
import org.amhzing.clusterview.web.api.user.UsersDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.web.controller.util.NameDtoFactory.convertName;

public final class UserDtoFactory {

    private UserDtoFactory() {
        // To prevent instantiation
    }

    public static UsersDTO users(final List<User> users) {
        final List<UserDTO> usersDto = users.stream()
                                            .map(UserDtoFactory::userDto)
                                            .collect(Collectors.toList());

        return new UsersDTO(usersDto);
    }

    private static UserDTO userDto(final User user) {
        return new UserDTO(user.getEmail().value(),
                           user.getPassword().value(),
                           convertName(user.getName()),
                           user.getState().active(),
                           roles(user.getRoles()),
                           countries(user.getCountries()));
    }

    private static List<RoleDTO> roles(final List<Role> roles) {
        return roles.stream()
                    .map(UserDtoFactory::roleDto)
                    .collect(Collectors.toList());
    }

    private static RoleDTO roleDto(final Role role) {
        return new RoleDTO(role.value());
    }

    private static List<CountryDTO> countries(final List<Country.Id> countries) {
        return countries.stream()
                        .map(country -> countryDto(country))
                        .collect(Collectors.toList());
    }

    private static CountryDTO countryDto(final Country.Id country) {
        return new CountryDTO(country.getId());
    }
}
