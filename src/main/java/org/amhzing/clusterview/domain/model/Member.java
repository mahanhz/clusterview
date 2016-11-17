package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class Member {

    private Name name;
    private Capability capability;
    private Commitment commitment;

    private Member(final Name name, final Capability capability, final Commitment commitment) {
        this.name = notNull(name);
        this.capability = capability;
        this.commitment = commitment;
    }

    public static Member create(final Name name, final Capability capability, final Commitment commitment) {
        return new Member(name, capability, commitment);
    }

    public Name getName() {
        return name;
    }

    public Capability getCapability() {
        return capability;
    }

    public Commitment getCommitment() {
        return commitment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Member member = (Member) o;
        return Objects.equals(name, member.name) &&
                Objects.equals(capability, member.capability) &&
                Objects.equals(commitment, member.commitment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capability, commitment);
    }

    @Override
    public String toString() {
        return "Member{" +
                "name=" + name +
                ", capability=" + capability +
                ", commitment=" + commitment +
                '}';
    }
}
