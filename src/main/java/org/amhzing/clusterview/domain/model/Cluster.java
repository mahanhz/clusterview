package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notBlank;

public class Cluster {

    private String name;
    private Set<Group> groups;

    private Cluster(final String name, final Set<Group> groups) {
        this.name = notBlank(name);
        this.groups = noNullElements(groups);
    }

    public static Cluster create(final String name, final Set<Group> groups) {
        return new Cluster(name, groups);
    }

    public String getName() {
        return name;
    }

    public Set<Group> getGroups() {
        return ImmutableSet.copyOf(groups);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cluster cluster = (Cluster) o;
        return Objects.equals(name, cluster.name) &&
                Objects.equals(groups, cluster.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, groups);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "name='" + name + '\'' +
                ", groups=" + groups +
                '}';
    }
}
