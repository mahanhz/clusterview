package org.amhzing.clusterview.web.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class UsersDTO extends ResourceSupport {

    @JsonProperty("users")
    public final List<UserDTO> users;

    public UsersDTO(final List<UserDTO> users) {
        this.users = notNull(users);
    }
}
