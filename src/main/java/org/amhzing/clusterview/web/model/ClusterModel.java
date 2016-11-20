package org.amhzing.clusterview.web.model;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ClusterModel {

    private Set<GroupModel> groups;

    private ClusterModel(final Set<GroupModel> groups) {
        this.groups = noNullElements(groups);
    }

    public static ClusterModel create(final Set<GroupModel> groups) {
        return new ClusterModel(groups);
    }

    public Set<GroupModel> getGroups() {
        return groups;
    }

    public void setGroups(final Set<GroupModel> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClusterModel that = (ClusterModel) o;
        return Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups);
    }

    @Override
    public String toString() {
        return "ClusterModel{" +
                "groups=" + groups +
                '}';
    }
}
