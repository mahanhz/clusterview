package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CapabilitiesDTO {

    @JsonProperty("capabilities")
    public final List<ReferenceActivityDTO> capabilities;

    @JsonCreator
    public CapabilitiesDTO(final List<ReferenceActivityDTO> capabilities) {
        this.capabilities = noNullElements(capabilities);
    }
}
