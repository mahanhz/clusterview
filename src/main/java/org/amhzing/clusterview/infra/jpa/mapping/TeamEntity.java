package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private Set<MemberEntity> members;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClusterEntity cluster;

    public TeamEntity() {
    }

    private TeamEntity(final Location location, final Set<MemberEntity> members, final ClusterEntity cluster) {
        this.location = location;
        this.members = members;
        this.cluster = cluster;
    }

    public static TeamEntity create(final Location location, final Set<MemberEntity> members, final ClusterEntity cluster) {
        return new TeamEntity(location, members, cluster);
    }

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
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", location=" + location +
                ", members=" + members +
                '}';
    }
}
