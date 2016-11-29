package org.amhzing.clusterview.web.model;

import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class ActivityStatisticModel {

    private Map<String, Long> activityQuantity;

    private ActivityStatisticModel(final Map<String, Long> activityQuantity) {
        this.activityQuantity = notNull(activityQuantity);
    }

    public static ActivityStatisticModel create(final Map<String, Long> activityQuantity) {
        return new ActivityStatisticModel(activityQuantity);
    }

    public Map<String, Long> getActivityQuantity() {
        return activityQuantity;
    }

    public void setActivityQuantity(final Map<String, Long> activityQuantity) {
        this.activityQuantity = notNull(activityQuantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityStatisticModel that = (ActivityStatisticModel) o;
        return Objects.equals(activityQuantity, that.activityQuantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityQuantity);
    }

    @Override
    public String toString() {
        return "ActivityStatisticModel{" +
                "activityQuantity=" + activityQuantity +
                '}';
    }
}
