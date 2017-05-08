package org.amhzing.clusterview.adapter.web.api.statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class HistoricalActivitiesDTO {

    @JsonProperty("currentActivities")
    public final ActivitiesDTO currentActivities;
    @JsonProperty("datedActivities")
    public final List<DatedActivitiesDTO> datedActivitiesDTO;

    public HistoricalActivitiesDTO(final ActivitiesDTO currentActivities, final List<DatedActivitiesDTO> datedActivitiesDTO) {
        this.currentActivities = notNull(currentActivities);
        this.datedActivitiesDTO = noNullElements(datedActivitiesDTO);
    }
}
