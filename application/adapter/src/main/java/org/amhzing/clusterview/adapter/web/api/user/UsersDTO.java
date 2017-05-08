package org.amhzing.clusterview.adapter.web.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class UsersDTO {

    @JsonProperty("users")
    public final List<UserDTO> users;

    public UsersDTO(final List<UserDTO> users) {
        this.users = notNull(users);
    }
}
