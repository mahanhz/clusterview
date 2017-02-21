package org.amhzing.clusterview.app.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class ActivityModel {

    private String id;
    private String name;

    public ActivityModel() {
    }

    private ActivityModel(final String id, final String name) {
        this.id = notBlank(id);
        this.name = notBlank(name);
    }

    public static ActivityModel create(final String id, final String name) {
        return new ActivityModel(id, name);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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
        if (o == null || getClass() != o.getClass()) return false;
        final ActivityModel that = (ActivityModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
