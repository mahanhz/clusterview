package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberEntity> members;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClusterEntity cluster;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Set<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(final Set<MemberEntity> members) {
        this.members = members;
    }

    public ClusterEntity getCluster() {
        return cluster;
    }

    public void setCluster(final ClusterEntity cluster) {
        this.cluster = cluster;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TeamEntity that = (TeamEntity) o;
        return id == that.id &&
                Objects.equals(location, that.location) &&
                Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, members);
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", location=" + location +
                ", members=" + members +
                '}';
    }
}
