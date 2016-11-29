package org.amhzing.clusterview.domain.model;

import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ActivityStatistic {

    private Map<Activity, Quantity> activityQuantity;

    private ActivityStatistic(final Map<Activity, Quantity> activityQuantity) {
        this.activityQuantity = notNull(activityQuantity);
    }

    public static ActivityStatistic create(final Map<Activity, Quantity> activityQuantity) {
        return new ActivityStatistic(activityQuantity);
    }

    public Map<Activity, Quantity> getActivityQuantity() {
        return activityQuantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityStatistic that = (ActivityStatistic) o;
        return Objects.equals(activityQuantity, that.activityQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityQuantity);
    }

    @Override
    public String toString() {
        return "ActivityStatistic{" +
                "activityQuantity=" + activityQuantity +
                '}';
    }
}
