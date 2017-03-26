package org.amhzing.clusterview.app.api.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class CourseDTO {

    @JsonProperty("id")
    public final String id;
    @JsonProperty("name")
    public final String name;
    @JsonProperty("quantity")
    public final int quantity;

    public CourseDTO(final String id, final String name, final int quantity) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
    }
}
