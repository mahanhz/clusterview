package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private Set<Member> members;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClusterEntity cluster;

    public Team() {
    }

    private Team(final Location location, final Set<Member> members, final ClusterEntity cluster) {
        this.location = location;
        this.members = members;
        this.cluster = cluster;
    }

    public static Team create(final Location location, final Set<Member> members, final ClusterEntity cluster) {
        return new Team(location, members, cluster);
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

    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(final Set<Member> members) {
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
        return "Team{" +
                "id=" + id +
                ", location=" + location +
                ", members=" + members +
                '}';
    }
}
