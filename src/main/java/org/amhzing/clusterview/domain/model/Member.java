package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class Member {

    private Id id;
    private Name name;
    private Capability capability;
    private Commitment commitment;

    private Member(final Name name, final Capability capability, final Commitment commitment) {
        this.name = name;
        this.capability = capability;
        this.commitment = commitment;
    }

    private Member(final Id id, final Name name, final Capability capability, final Commitment commitment) {
        this.id = notNull(id);
        this.name = notNull(name);
        this.capability = capability;
        this.commitment = commitment;
    }

    public static Member create(final Id id, final Name name, final Capability capability, final Commitment commitment) {
        return new Member(id, name, capability, commitment);
    }

    public static Member create(final Name name, final Capability capability, final Commitment commitment) {
        return new Member(name, capability, commitment);
    }

    public Id getId() {
        return id;
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
        return Objects.equals(id, member.id) &&
                Objects.equals(name, member.name) &&
                Objects.equals(capability, member.capability) &&
                Objects.equals(commitment, member.commitment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capability, commitment);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name=" + name +
                ", capability=" + capability +
                ", commitment=" + commitment +
                '}';
    }

    public static class Id {

        private long id;

        private Id(final long id) {
            this.id = id;
        }

        public static Id create(final long id) {
            return new Id(id);
        }

        public long getId() {
            return id;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Id id1 = (Id) o;
            return id == id1.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
