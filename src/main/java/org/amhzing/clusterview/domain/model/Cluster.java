package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;

public class Cluster {

    private Set<Group> groups;

    private Cluster(final Set<Group> groups) {
        this.groups = noNullElements(groups);
    }

    public static Cluster create(final Set<Group> groups) {
        return new Cluster(groups);
    }

    public Set<Group> getGroups() {
        return ImmutableSet.copyOf(groups);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cluster cluster = (Cluster) o;
        return Objects.equals(groups, cluster.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "groups=" + groups +
                '}';
    }
}
