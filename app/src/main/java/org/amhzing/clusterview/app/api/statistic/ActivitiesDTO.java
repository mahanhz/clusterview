package org.amhzing.clusterview.app.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ActivitiesDTO extends ResourceSupport {

    @JsonProperty("activities")
    public final List<ActivityDTO> activities;
    @JsonProperty("coreActivities")
    public final List<CoreActivityDTO> coreActivities;

    @JsonCreator
    public ActivitiesDTO(final List<ActivityDTO> activities, final List<CoreActivityDTO> coreActivities) {
        this.activities = noNullElements(activities);
        this.coreActivities = noNullElements(coreActivities);
    }
}
