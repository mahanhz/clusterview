package org.amhzing.clusterview.app.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public DatedActivitiesDTO(final Date date, final ActivitiesDTO activities) {
        this.date = notNull(date);
        this.activities = notNull(activities);
    }
}
