package org.amhzing.clusterview.api.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class ActivityDTO {

    @JsonProperty("name")
    public final String name;
    @JsonProperty("quanity")
    public final int quantity;

    public ActivityDTO(final String name, final int quantity) {
        this.name = notBlank(name);
        this.quantity = quantity;
    }
}
