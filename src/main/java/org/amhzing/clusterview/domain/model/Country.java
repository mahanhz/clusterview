package org.amhzing.clusterview.domain.model;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public class Country implements Serializable {

    private static final int MIN_CODE_LENGTH = 2;
    private static final int MAX_CODE_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 100;

    private String code;
    private String name;

    private Country(final String code, final String name) {
        isValidCode(code);
        isValidName(name);

        this.code = trim(code);
        this.name = trim(name);
    }

    public static Country create(final String code, final String name) {
        return new Country(code, name);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Country country = (Country) o;
        return Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    private boolean isValidCode(final String value) {
        notBlank(value);
        isTrue(value.length() >= MIN_CODE_LENGTH && value.length() <= MAX_CODE_LENGTH);

        return true;
    }

    private boolean isValidName(final String value) {
        isNotBlank(value);
        isTrue(trim(value).length() <= MAX_NAME_LENGTH);

        return true;
    }
}
