package org.amhzing.clusterview.app.web.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;

public final class CommitmentModel {

    @NotNull
    private List<ActivityModel> activities;

    public CommitmentModel() {
        activities = new ArrayList<>();
    }

    private CommitmentModel(final List<ActivityModel> activities) {
        this.activities = noNullElements(activities);
    }

    public static CommitmentModel create(final List<ActivityModel> activities) {
        return new CommitmentModel(activities);
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
