package org.amhzing.clusterview.backend.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;

public final class ClusterModel {

    private List<GroupModel> groups;

    public ClusterModel() {
        groups = new ArrayList<>();
    }

    private ClusterModel(final List<GroupModel> groups) {
        this.groups = noNullElements(groups);
    }

    public static ClusterModel create(final List<GroupModel> groups) {
        return new ClusterModel(groups);
    }

    public List<GroupModel> getGroups() {
        return groups;
    }

    public void setGroups(final List<GroupModel> groups) {
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
