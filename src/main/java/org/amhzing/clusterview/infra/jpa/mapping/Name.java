package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;

    public Name() {
    }

    private Name(final String firstName, final String middleName, final String lastName, final String suffix) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
    }

    public static Name create(final String firstName, final String middleName, final String lastName, final String suffix) {
        return new Name(firstName, middleName, lastName, suffix);
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

    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
