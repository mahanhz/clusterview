package org.amhzing.clusterview.app.web.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class CourseStatisticModel {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @Min(value = 0, message = "Value must be 0 or more")
    private int quantity;

    public CourseStatisticModel() {

    }

    private CourseStatisticModel(final String id, final String name, final int quantity) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
    }

    public static CourseStatisticModel create(final String id, final String name, final int quantity) {
        return new CourseStatisticModel(id, name, quantity);
    }

    public static CourseStatisticModel empty() {
        return create("", "", 0);
    }

    public boolean isZero() {
        return quantity == 0;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
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
