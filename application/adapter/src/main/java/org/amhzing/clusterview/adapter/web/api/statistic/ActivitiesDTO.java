package org.amhzing.clusterview.adapter.web.api.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ActivitiesDTO {

    @JsonProperty("activities")
    public final List<ActivityDTO> activities;
    @JsonProperty("coreActivities")
    public final List<CoreActivityDTO> coreActivities;

    public ActivitiesDTO(final List<ActivityDTO> activities, final List<CoreActivityDTO> coreActivities) {
        this.activities = noNullElements(activities);
        this.coreActivities = noNullElements(coreActivities);
    }
}
