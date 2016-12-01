package org.amhzing.clusterview.domain.model.statistic;

import org.amhzing.clusterview.domain.model.Activity;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class ActivityStatistic {

    private Map<Activity, Quantity> activityQuantity;
    private Set<CoreActivity> coreActivities;

    public ActivityStatistic() {
        activityQuantity = emptyMap();
        coreActivities = emptySet();
    }

    private ActivityStatistic(final Map<Activity, Quantity> activityQuantity, final Set<CoreActivity> coreActivities) {
        this.activityQuantity = notNull(activityQuantity);;
        this.coreActivities = noNullElements(coreActivities);
    }

    public static ActivityStatistic create(final Map<Activity, Quantity> activityQuantity, final Set<CoreActivity> coreActivities) {
        return new ActivityStatistic(activityQuantity, coreActivities);
    }

    public Map<Activity, Quantity> getActivityQuantity() {
        return activityQuantity;
    }

    public Set<CoreActivity> getCoreActivities() {
        return coreActivities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityStatistic that = (ActivityStatistic) o;
        return Objects.equals(activityQuantity, that.activityQuantity) &&
                Objects.equals(coreActivities, that.coreActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityQuantity, coreActivities);
    }

    @Override
    public String toString() {
        return "ActivityStatistic{" +
                "activityQuantity=" + activityQuantity +
                ", coreActivities=" + coreActivities +
                '}';
    }
}
