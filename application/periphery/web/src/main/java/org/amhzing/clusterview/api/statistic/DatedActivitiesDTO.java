package org.amhzing.clusterview.api.statistic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Date;

import static org.apache.commons.lang3.Validate.notNull;

public class DatedActivitiesDTO {

    @JsonProperty("date")
    @JsonFormat(pattern="yyyy-MM")
    public final Date date;
    @JsonUnwrapped
    public final ActivitiesDTO activities;

    public DatedActivitiesDTO(final Date date, final ActivitiesDTO activities) {
        this.date = notNull(date);
        this.activities = notNull(activities);
    }
}
