package org.amhzing.clusterview.app.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class CountryDTO {

    @JsonProperty("name")
    public final String name;

    public CountryDTO(final String name) {
        this.name = notBlank(name);
    }
}
