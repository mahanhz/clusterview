package org.amhzing.clusterview.appui.web.model;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class MemberModel {

    private String obfuscatedId;

    @NotNull @Valid
    private NameModel name;

    @NotNull @Valid
    private CapabilityModel capability;

    @NotNull @Valid
    private CommitmentModel commitment;

    public MemberModel() {
        name = new NameModel();
        capability = new CapabilityModel();
        commitment = new CommitmentModel();
    }

    private MemberModel(final String obfuscatedId, final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        this.obfuscatedId = notBlank(obfuscatedId);
        this.name = notNull(name);
        this.capability = notNull(capability);
        this.commitment = notNull(commitment);
    }

    public static MemberModel create(final String obfuscatedId, final NameModel name, final CapabilityModel capability, final CommitmentModel commitment) {
        return new MemberModel(obfuscatedId, name, capability, commitment);
    }

    public String getObfuscatedId() {
        return obfuscatedId;
    }

    public void setObfuscatedId(final String obfuscatedId) {
        this.obfuscatedId = obfuscatedId;
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

    public boolean isEmpty() {
        return name.isEmpty() &&
                CollectionUtils.isEmpty(capability.getActivities()) &&
                CollectionUtils.isEmpty(commitment.getActivities());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberModel)) return false;
        final MemberModel that = (MemberModel) o;
        return Objects.equals(obfuscatedId, that.obfuscatedId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(capability, that.capability) &&
                Objects.equals(commitment, that.commitment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(obfuscatedId, name, capability, commitment);
    }

    @Override
    public String toString() {
        return "MemberModel{" +
                "obfuscatedId='" + obfuscatedId + '\'' +
                ", name=" + name +
                ", capability=" + capability +
                ", commitment=" + commitment +
                '}';
    }
}
