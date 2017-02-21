package org.amhzing.clusterview.app.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "member")
public final class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Embedded
    private Name name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CapabilityEntity> capabilities;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommitmentEntity> commitments;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamEntity team;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(final Name name) {
        this.name = name;
    }

    public Set<CapabilityEntity> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(final Set<CapabilityEntity> capabilities) {
        this.capabilities = capabilities;
    }

    public Set<CommitmentEntity> getCommitments() {
        return commitments;
    }

    public void setCommitments(final Set<CommitmentEntity> commitments) {
        this.commitments = commitments;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(final TeamEntity team) {
        this.team = team;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MemberEntity that = (MemberEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(capabilities, that.capabilities) &&
                Objects.equals(commitments, that.commitments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capabilities, commitments);
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", name=" + name +
                ", capabilities=" + capabilities +
                ", commitments=" + commitments +
                '}';
    }
}
