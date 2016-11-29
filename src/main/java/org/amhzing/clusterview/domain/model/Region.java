package org.amhzing.clusterview.domain.model;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.*;

public final class Region {

    private Id id;
    private Set<Cluster> clusters;

    private Region(final Id id, final Set<Cluster> clusters) {
        this.id = notNull(id);
        this.clusters = noNullElements(clusters);
    }

    public static Region create(final Id id, final Set<Cluster> clusters) {
        return new Region(id, clusters);
    }

    public Id getId() {
        return id;
    }

    public Set<Cluster> getClusters() {
        return clusters;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Region region = (Region) o;
        return Objects.equals(id, region.id) &&
                Objects.equals(clusters, region.clusters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clusters);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", clusters=" + clusters +
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
