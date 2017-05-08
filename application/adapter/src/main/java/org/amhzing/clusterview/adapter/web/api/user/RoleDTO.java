package org.amhzing.clusterview.adapter.web.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class RoleDTO {

    @JsonProperty("name")
    public final String name;

    public RoleDTO(final String name) {
        this.name = notBlank(name);
    }
}
