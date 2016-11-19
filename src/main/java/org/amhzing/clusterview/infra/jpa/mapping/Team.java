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
    private Cluster cluster;

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

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(final Cluster cluster) {
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
