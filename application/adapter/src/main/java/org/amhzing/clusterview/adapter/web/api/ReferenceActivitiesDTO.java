package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ReferenceActivitiesDTO {

    @JsonProperty("activities")
    public final List<ReferenceActivityDTO> activities;

    public ReferenceActivitiesDTO(final List<ReferenceActivityDTO> activities) {
        this.activities = noNullElements(activities);
    }
}
