package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

public class LocationDTO {

    @JsonProperty("x")
    public final int x;
    @JsonProperty("y")
    public final int y;

    @JsonCreator
    public LocationDTO(@JsonProperty("x") final int x,
                       @JsonProperty("y") final int y) {
        Validate.isTrue(x > 0, "Location x must be greater than 0");
        Validate.isTrue(y > 0, "Location y must be greater than 0");

        this.x = x;
        this.y = y;
    }
}
