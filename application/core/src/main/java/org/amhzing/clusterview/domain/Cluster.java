package org.amhzing.clusterview.domain;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.*;

public final class Cluster {

    private final Id id;
    private final Set<Group> groups;

    private Cluster(final Id id, final Set<Group> groups) {
        noNullElements(groups);

        this.id = notNull(id);
        this.groups = ImmutableSet.copyOf(groups);
    }

    public static Cluster create(final Id id, final Set<Group> groups) {
        return new Cluster(id, groups);
    }

    public Id getId() {
        return id;
    }

    public Set<Group> getGroups() {
        return ImmutableSet.copyOf(groups);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cluster cluster = (Cluster) o;
        return Objects.equals(id, cluster.id) &&
                Objects.equals(groups, cluster.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groups);
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", groups=" + groups +
                '}';
    }

    public static class Id {

        private String id;

        private Id(final String id) {
            this.id = notBlank(id);
        }

        public static Id create(final String id) {
            return new Id(id);
        }

        public String getId() {
            return id;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Id id1 = (Id) o;
            return Objects.equals(id, id1.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
