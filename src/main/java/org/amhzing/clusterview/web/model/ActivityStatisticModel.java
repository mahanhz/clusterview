package org.amhzing.clusterview.web.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class ActivityStatisticModel {

    private Map<String, Long> activityQuantity;
    private List<CoreActivityModel> coreActivities;

    private ActivityStatisticModel(final Map<String, Long> activityQuantity, final List<CoreActivityModel> coreActivities) {
        this.activityQuantity = notNull(activityQuantity);;
        this.coreActivities = noNullElements(coreActivities);
    }

    public static ActivityStatisticModel create(final Map<String, Long> activityQuantity, final List<CoreActivityModel> coreActivities) {
        return new ActivityStatisticModel(activityQuantity, coreActivities);
    }

    public Map<String, Long> getActivityQuantity() {
        return activityQuantity;
    }

    public void setActivityQuantity(final Map<String, Long> activityQuantity) {
        this.activityQuantity = activityQuantity;
    }

    public List<CoreActivityModel> getCoreActivities() {
        return coreActivities;
    }

    public void setCoreActivities(final List<CoreActivityModel> coreActivities) {
        this.coreActivities = coreActivities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityStatisticModel that = (ActivityStatisticModel) o;
        return Objects.equals(activityQuantity, that.activityQuantity) &&
                Objects.equals(coreActivities, that.coreActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityQuantity, coreActivities);
    }

    @Override
    public String toString() {
        return "ActivityStatisticModel{" +
                "activityQuantity=" + activityQuantity +
                ", coreActivities=" + coreActivities +
                '}';
    }
}
