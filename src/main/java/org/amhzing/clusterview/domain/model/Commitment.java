package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;

public class Commitment {

    private Set<Activity> activities;

    private Commitment(final Set<Activity> activities) {
        this.activities = noNullElements(activities);
    }

    public static Commitment create(final Set<Activity> activities) {
        return new Commitment(activities);
    }

    public Set<Activity> getActivities() {
        return ImmutableSet.copyOf(activities);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Commitment that = (Commitment) o;
        return Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activities);
    }

    @Override
    public String toString() {
        return "Commitment{" +
                "activities=" + activities +
                '}';
    }
}
