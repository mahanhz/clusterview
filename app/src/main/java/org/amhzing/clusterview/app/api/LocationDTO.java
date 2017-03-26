package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDTO {

    @JsonProperty("x")
    public final int x;
    @JsonProperty("y")
    public final int y;

    @JsonCreator
    public LocationDTO(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
