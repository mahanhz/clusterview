package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notBlank;

public class Region {

    private String name;
    private Set<Cluster> cluster;

    private Region(final String name, final Set<Cluster> cluster) {
        this.name = notBlank(name);
        this.cluster = noNullElements(cluster);
    }

    public static Region create(final String name, final Set<Cluster> cluster) {
        return new Region(name, cluster);
    }

    public String getName() {
        return name;
    }

    public Set<Cluster> getCluster() {
        return ImmutableSet.copyOf(cluster);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Region region = (Region) o;
        return Objects.equals(name, region.name) &&
                Objects.equals(cluster, region.cluster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cluster);
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", cluster=" + cluster +
                '}';
    }
}
