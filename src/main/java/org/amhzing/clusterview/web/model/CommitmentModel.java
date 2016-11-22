package org.amhzing.clusterview.web.model;

import com.google.common.collect.ImmutableSet;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;

public class CommitmentModel {

    @NotNull
    private Set<ActivityModel> activities;

    private CommitmentModel(final Set<ActivityModel> activities) {
        this.activities = noNullElements(activities);
    }

    public static CommitmentModel create(final Set<ActivityModel> activities) {
        return new CommitmentModel(activities);
    }

    public Set<ActivityModel> getActivities() {
        return ImmutableSet.copyOf(activities);
    }

    public void setActivities(final Set<ActivityModel> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CommitmentModel that = (CommitmentModel) o;
        return Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activities);
    }

    @Override
    public String toString() {
        return "CommitmentModel{" +
                "activities=" + activities +
                '}';
    }
}
