package org.amhzing.clusterview.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ReferenceActivitiesDTO extends ResourceSupport {

    @JsonProperty("activities")
    public final List<ReferenceActivityDTO> activities;

    public ReferenceActivitiesDTO(final List<ReferenceActivityDTO> activities) {
        this.activities = noNullElements(activities);
    }
}
