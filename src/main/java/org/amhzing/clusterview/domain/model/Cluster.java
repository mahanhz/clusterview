package org.amhzing.clusterview.domain.model;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class Cluster {

    private Id id;
    private Set<Group> groups;

    private Cluster(final Id id, final Set<Group> groups) {
        this.id = notNull(id);
        this.groups = noNullElements(groups);
    }

    public static Cluster create(final Id id, final Set<Group> groups) {
        return new Cluster(id, groups);
    }

    public Id getId() {
        return id;
    }

    public Set<Group> getGroups() {
        return groups;
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
        public String toString() {
            return "Id{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
