package org.amhzing.clusterview.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class ClusterNameModel {

    @NotEmpty(message = "A cluster must be selected")
    private String name;

    public ClusterNameModel() {
    }

    private ClusterNameModel(final String name) {
        this.name = notBlank(name);
    }

    public static ClusterNameModel create(final String name) {
        return new ClusterNameModel(name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof ClusterNameModel)) return false;
        final ClusterNameModel that = (ClusterNameModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ClusterNameModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
