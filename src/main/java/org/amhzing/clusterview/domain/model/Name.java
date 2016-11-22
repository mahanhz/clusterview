package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class Name {

    private FirstName firstName;
    private MiddleName middleName;
    private LastName lastName;
    private Suffix suffix;

    private Name(final FirstName firstName, final MiddleName middleName, final LastName lastName, final Suffix suffix) {
        this.firstName = notNull(firstName);
        this.middleName = middleName;
        this.lastName = notNull(lastName);
        this.suffix = suffix;
    }

    public static Name create(final FirstName firstName,
                              final MiddleName middleName,
                              final LastName lastName,
                              final Suffix suffix) {
        return new Name(firstName, middleName, lastName, suffix);
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public MiddleName getMiddleName() {
        return middleName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Suffix getSuffix() {
        return suffix;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name name = (Name) o;
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
        return "Name{" +
                "firstName=" + firstName +
                ", middleName=" + middleName +
                ", lastName=" + lastName +
                ", suffix=" + suffix +
                '}';
    }
}
