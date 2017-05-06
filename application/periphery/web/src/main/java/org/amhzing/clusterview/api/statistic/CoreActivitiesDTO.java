package org.amhzing.clusterview.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CoreActivitiesDTO extends ResourceSupport {

    @JsonProperty("coreActivities")
    public final List<CoreActivityDTO> coreActivities;

    @JsonCreator
    public CoreActivitiesDTO(@JsonProperty("coreActivities") final List<CoreActivityDTO> coreActivities) {
        this.coreActivities = noNullElements(coreActivities);
    }
}
