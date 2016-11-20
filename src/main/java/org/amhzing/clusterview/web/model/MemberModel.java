package org.amhzing.clusterview.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class MemberModel {

    private NameModel name;
    private CapabilityModel capability;
    private CommitmentModel commitment;

    private MemberModel(final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        this.name = notNull(name);
        this.capability = capability;
        this.commitment = commitment;
    }

    public static MemberModel create(final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        return new MemberModel(name, capability, commitment);
    }

    public NameModel getName() {
        return name;
    }

    public void setName(final NameModel name) {
        this.name = name;
    }

    public CapabilityModel getCapability() {
        return capability;
    }

    public void setCapability(final CapabilityModel capability) {
        this.capability = capability;
    }

    public CommitmentModel getCommitment() {
        return commitment;
    }

    public void setCommitment(final CommitmentModel commitment) {
        this.commitment = commitment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MemberModel that = (MemberModel) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(capability, that.capability) &&
                Objects.equals(commitment, that.commitment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capability, commitment);
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "name=" + name +
                ", capability=" + capability +
                ", commitment=" + commitment +
                '}';
    }
}
