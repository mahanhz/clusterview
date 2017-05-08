package org.amhzing.clusterview.adapter.web.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CoreActivitiesDTO {

    @JsonProperty("coreActivities")
    public final List<CoreActivityDTO> coreActivities;

    @JsonCreator
    public CoreActivitiesDTO(@JsonProperty("coreActivities") final List<CoreActivityDTO> coreActivities) {
        this.coreActivities = noNullElements(coreActivities);
    }
}
