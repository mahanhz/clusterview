package org.amhzing.clusterview.appui.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class DatedActivityStatisticModel {

    @JsonFormat(pattern="yyyy-MM")
    private Date date;
    private ActivityStatisticModel activityStatisticModel;

    private DatedActivityStatisticModel(final Date date, final ActivityStatisticModel activityStatisticModel) {
        this.date = notNull(date);
        this.activityStatisticModel = notNull(activityStatisticModel);
    }

    public static DatedActivityStatisticModel create(final Date date, final ActivityStatisticModel activityStatisticModel) {
        return new DatedActivityStatisticModel(date, activityStatisticModel);
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(final Date date) {
        this.date = new Date(date.getTime());
    }

    public ActivityStatisticModel getActivityStatisticModel() {
        return activityStatisticModel;
    }

    public void setActivityStatisticModel(final ActivityStatisticModel activityStatisticModel) {
        this.activityStatisticModel = activityStatisticModel;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DatedActivityStatisticModel)) return false;
        final DatedActivityStatisticModel that = (DatedActivityStatisticModel) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(activityStatisticModel, that.activityStatisticModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, activityStatisticModel);
    }

    @Override
    public String toString() {
        return "DatedActivityStatisticModel{" +
                "date=" + date +
                ", activityStatisticModel=" + activityStatisticModel +
                '}';
    }
}
