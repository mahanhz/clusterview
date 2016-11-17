package org.amhzing.clusterview.domain.model;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Suffix implements Serializable {

    protected static final int MAX_LENGTH = 10;

    private String value;

    private Suffix(final String value) {
        isValid(value);

        this.value = trim(value);
    }

    public static Suffix create(final String value) {
        return new Suffix(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suffix suffix = (Suffix) o;
        return Objects.equals(value, suffix.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Suffix{" +
                "value='" + value + '\'' +
                '}';
    }

    private boolean isValid(final String value) {
        notBlank(value);
        isTrue(trim(value).length() <= MAX_LENGTH);

        return true;
    }
}
