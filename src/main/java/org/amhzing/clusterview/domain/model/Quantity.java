package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

public class Quantity {

    public static final int MIN_QUANTITY = 0;

    private long value;

    private Quantity(final long value) {
        isTrue(value >= MIN_QUANTITY);

        this.value = value;
    }

    public static Quantity create(final long value) {
        return new Quantity(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "value=" + value +
                '}';
    }
}
