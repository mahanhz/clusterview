package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class ClusterDTO {

    @JsonProperty("name")
    public final String name;

    @JsonCreator
    public ClusterDTO(final String name) {
        this.name = notBlank(name);
    }
}
