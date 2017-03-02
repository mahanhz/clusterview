package org.amhzing.clusterview.app.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class CourseStatisticModel {

    private String id;
    private String name;
    private int quantity;

    private CourseStatisticModel(final String id, final String name, final int quantity) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
    }

    public static CourseStatisticModel create(final String id, final String name, final int quantity) {
        return new CourseStatisticModel(id, name, quantity);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseStatisticModel)) return false;
        final CourseStatisticModel that = (CourseStatisticModel) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }

    @Override
    public String toString() {
        return "CourseStatisticModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
