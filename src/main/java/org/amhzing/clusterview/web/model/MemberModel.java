package org.amhzing.clusterview.web.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public final class MemberModel {

    private long id;

    @NotNull @Valid
    private NameModel name;

    @NotNull @Valid
    private CapabilityModel capability;

    @NotNull @Valid
    private CommitmentModel commitment;

    private MemberModel(final long id, final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        this.id = id;
        this.name = notNull(name);
        this.capability = notNull(capability);
        this.commitment = notNull(commitment);
    }

    public static MemberModel create(final long id, final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        return new MemberModel(id, name, capability, commitment);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
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
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(capability, that.capability) &&
                Objects.equals(commitment, that.commitment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capability, commitment);
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "id=" + id +
                ", name=" + name +
                ", capability=" + capability +
                ", commitment=" + commitment +
                '}';
    }
}
