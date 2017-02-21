package org.amhzing.clusterview.app.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;

public final class Capability {

    private final Set<Activity> activities;

    private Capability(final Set<Activity> activities) {
        noNullElements(activities);
        this.activities = ImmutableSet.copyOf(activities);
    }

    public static Capability create(final Set<Activity> activities) {
        return new Capability(activities);
    }

    public Set<Activity> getActivities() {
        return ImmutableSet.copyOf(activities);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Capability that = (Capability) o;
        return Objects.equals(activities, that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activities);
    }

    @Override
    public String toString() {
        return "Capability{" +
                "activities=" + activities +
                '}';
    }
}
