package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Name name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Capability> capabilities;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Commitment> commitments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

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

    public Set<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(final Set<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public Set<Commitment> getCommitments() {
        return commitments;
    }

    public void setCommitments(final Set<Commitment> commitments) {
        this.commitments = commitments;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name=" + name +
                ", capabilities=" + capabilities +
                '}';
    }
}
