package org.amhzing.clusterview.appui.web.model;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class NameModel implements Serializable {

    @NotEmpty(message = "First name is required")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    private String suffix;

    public NameModel() {
    }

    private NameModel(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = notBlank(lastName);
        this.suffix = suffix;
    }

    public static NameModel create(final String firstName, final String middleName, final String lastName, final String suffix) {
        return new NameModel(firstName, middleName, lastName, suffix);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(firstName) &&
                StringUtils.isBlank(middleName) &&
                StringUtils.isBlank(lastName) &&
                StringUtils.isBlank(suffix);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NameModel name = (NameModel) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(middleName, name.middleName) &&
                Objects.equals(lastName, name.lastName) &&
                Objects.equals(suffix, name.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, suffix);
    }

    @Override
    public String toString() {
        return "NameModel{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
