package org.amhzing.clusterview.core.domain.statistic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.core.domain.Activity;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class ActivityStatistic implements StatsHistoryElement {

    private final Map<Activity, Quantity> activityQuantity;
    private final Set<CoreActivity> coreActivities;

    public ActivityStatistic() {
        activityQuantity = emptyMap();
        coreActivities = emptySet();
    }

    private ActivityStatistic(final Map<Activity, Quantity> activityQuantity, final Set<CoreActivity> coreActivities) {
        notNull(activityQuantity);
        noNullElements(coreActivities);

        this.activityQuantity = ImmutableMap.copyOf(activityQuantity);
        this.coreActivities = ImmutableSet.copyOf(coreActivities);
    }

    public static ActivityStatistic create(final Map<Activity, Quantity> activityQuantity, final Set<CoreActivity> coreActivities) {
        return new ActivityStatistic(activityQuantity, coreActivities);
    }

    public static ActivityStatistic empty() {
        return ActivityStatistic.create(emptyMap(), emptySet());
    }

    public Map<Activity, Quantity> getActivityQuantity() {
        return ImmutableMap.copyOf(activityQuantity);
    }

    public Set<CoreActivity> getCoreActivities() {
        return ImmutableSet.copyOf(coreActivities);
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

    @Override
    public Optional<CoreActivity> accept(final CoreActivityStatsHistoryVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public int accept(final ActivityStatsHistoryVisitor visitor) {
        return visitor.visit(this);
    }
}
