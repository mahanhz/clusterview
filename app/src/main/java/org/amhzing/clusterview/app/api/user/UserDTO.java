package org.amhzing.clusterview.app.api.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.amhzing.clusterview.app.api.NameDTO;

import java.util.List;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class UserDTO {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String ENABLED = "enabled";
    public static final String ROLES = "roles";
    public static final String COUNTRIES = "countries";

    @JsonProperty(EMAIL)
    public final String email;
    @JsonProperty(PASSWORD)
    public final String password;
    @JsonProperty(NAME)
    public final NameDTO name;
    @JsonProperty(ENABLED)
    public final boolean enabled;
    @JsonProperty(ROLES)
    public final List<RoleDTO> roles;
    @JsonProperty(COUNTRIES)
    public final List<CountryDTO> countries;

    @JsonCreator
    public UserDTO(@JsonProperty(EMAIL) final String email,
                   @JsonProperty(PASSWORD) final String password,
                   @JsonProperty(NAME) final NameDTO name,
                   @JsonProperty(ENABLED) final boolean enabled,
                   @JsonProperty(ROLES) final List<RoleDTO> roles,
                   @JsonProperty(COUNTRIES) final List<CountryDTO> countries) {
        this.email = notBlank(email);
        this.password = notBlank(password);
        this.name = notNull(name);
        this.enabled = enabled;
        this.roles = notNull(roles);
        this.countries = notNull(countries);
    }
}
