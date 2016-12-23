package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public final class LastName {

    protected static final int MAX_LENGTH = 25;

    private final String value;

    private LastName(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static LastName create(final String value) {
        return new LastName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(value, lastName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "LastName{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(trim(value).length() <= MAX_LENGTH);

        return true;
    }
}
