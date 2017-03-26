package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CommitmentsDTO {

    @JsonProperty("commitments")
    public final List<ReferenceActivityDTO> commitments;

    @JsonCreator
    public CommitmentsDTO(@JsonProperty("commitments") final List<ReferenceActivityDTO> commitments) {
        this.commitments = noNullElements(commitments);
    }
}
