package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class RegionDTO {

    @JsonProperty("name")
    public final String name;

    public RegionDTO(final String name) {
        this.name = notBlank(name);
    }
}
