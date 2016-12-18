package org.amhzing.clusterview.domain.model.statistic;

import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class DatedActivityStatistic {

    private Date date;
    private ActivityStatistic activityStatistic;

    private DatedActivityStatistic(final Date date, final ActivityStatistic activityStatistic) {
        this.date = notNull(date);
        this.activityStatistic = notNull(activityStatistic);
    }

    public static DatedActivityStatistic create(final Date date, final ActivityStatistic activityStatistic) {
        return new DatedActivityStatistic(date, activityStatistic);
    }

    public Date getDate() {
        return date;
    }

    public ActivityStatistic getActivityStatistic() {
        return activityStatistic;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DatedActivityStatistic)) return false;
        final DatedActivityStatistic that = (DatedActivityStatistic) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(activityStatistic, that.activityStatistic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, activityStatistic);
    }

    @Override
    public String toString() {
        return "DatedActivityStatistic{" +
                "date=" + date +
                ", activityStatistic=" + activityStatistic +
                '}';
    }
}
