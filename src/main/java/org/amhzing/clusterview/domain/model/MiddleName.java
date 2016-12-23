package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public final class MiddleName {

    protected static final int MAX_LENGTH = 25;

    private final String value;

    private MiddleName(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static MiddleName create(final String value) {
        return new MiddleName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiddleName that = (MiddleName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MiddleName{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(trim(value).length() <= MAX_LENGTH);

        return true;
    }
}
