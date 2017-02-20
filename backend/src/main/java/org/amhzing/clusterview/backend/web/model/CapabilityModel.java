package org.amhzing.clusterview.backend.web.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;

public final class CapabilityModel {

    @NotNull
    private List<ActivityModel> activities;

    public CapabilityModel() {
        activities = new ArrayList<>();
    }

    private CapabilityModel(final List<ActivityModel> activities) {
        this.activities = noNullElements(activities);
    }

    public static CapabilityModel create(final List<ActivityModel> activities) {
        return new CapabilityModel(activities);
    }

    public List<ActivityModel> getActivities() {
        return activities;
    }

    public void setActivities(final List<ActivityModel> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CapabilityModel that = (CapabilityModel) o;
        return Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activities);
    }

    @Override
    public String toString() {
        return "CapabilityModel{" +
                "activities=" + activities +
                '}';
    }
}
