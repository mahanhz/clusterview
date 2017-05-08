package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class ClusterDTO {

    @JsonProperty("name")
    public final String name;

    public ClusterDTO(final String name) {
        this.name = notBlank(name);
    }
}
