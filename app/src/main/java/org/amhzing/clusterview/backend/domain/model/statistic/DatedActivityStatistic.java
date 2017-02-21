package org.amhzing.clusterview.backend.domain.model.statistic;

import java.util.Date;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class DatedActivityStatistic {

    private final Date date;
    private final ActivityStatistic activityStatistic;

    private DatedActivityStatistic(final Date date, final ActivityStatistic activityStatistic) {
        notNull(date);
        this.date = new Date(date.getTime());
        this.activityStatistic = notNull(activityStatistic);
    }

    public static DatedActivityStatistic create(final Date date, final ActivityStatistic activityStatistic) {
        return new DatedActivityStatistic(date, activityStatistic);
    }

    public Date getDate() {
        return new Date(date.getTime());
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
