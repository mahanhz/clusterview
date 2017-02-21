package org.amhzing.clusterview.backend.web.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class GroupsStatisticsModel {

    private Set<GroupModel> groups;
    private ActivityStatisticModel statistics;

    private GroupsStatisticsModel(final Set<GroupModel> groups, final ActivityStatisticModel statistics) {
        this.groups = noNullElements(groups);
        this.statistics = notNull(statistics);
    }

    public static GroupsStatisticsModel create(final Set<GroupModel> groups, final ActivityStatisticModel statistics) {
        return new GroupsStatisticsModel(groups, statistics);
    }

    public Set<GroupModel> getGroups() {
        return ImmutableSet.copyOf(groups);
    }

    public void setGroups(final Set<GroupModel> groups) {
        this.groups = ImmutableSet.copyOf(groups);
    }

    public ActivityStatisticModel getStatistics() {
        return statistics;
    }

    public void setStatistics(final ActivityStatisticModel statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupsStatisticsModel)) return false;
        final GroupsStatisticsModel that = (GroupsStatisticsModel) o;
        return Objects.equals(groups, that.groups) &&
                Objects.equals(statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, statistics);
    }

    @Override
    public String toString() {
        return "GroupsStatisticsModel{" +
                "groups=" + groups +
                ", statistics=" + statistics +
                '}';
    }
}
