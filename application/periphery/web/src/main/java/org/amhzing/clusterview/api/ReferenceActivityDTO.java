package org.amhzing.clusterview.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class ReferenceActivityDTO {

    @JsonProperty("id")
    public final String id;
    @JsonProperty("name")
    public final String name;

    @JsonCreator
    public ReferenceActivityDTO(@JsonProperty("id") final String id,
                                @JsonProperty("name") final String name) {
        this.id = notBlank(id);
        this.name = notBlank(name);
    }
}
