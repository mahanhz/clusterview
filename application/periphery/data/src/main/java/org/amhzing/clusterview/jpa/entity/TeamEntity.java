package org.amhzing.clusterview.jpa.entity;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "team")
public final class TeamEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberEntity> members;

    @ElementCollection
    @CollectionTable(name = "teams_core_activities", joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "core_activity_id", referencedColumnName = "id")
    private Map<CoreActivityEntity, ParticipantQuantity> coreActivities;

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

    public Map<CoreActivityEntity, ParticipantQuantity> getCoreActivities() {
        return coreActivities;
    }

    public void setCoreActivities(final Map<CoreActivityEntity, ParticipantQuantity> coreActivities) {
        this.coreActivities = coreActivities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TeamEntity that = (TeamEntity) o;
        return id == that.id &&
                Objects.equals(location, that.location) &&
                Objects.equals(members, that.members) &&
                Objects.equals(coreActivities, that.coreActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, members, coreActivities);
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", location=" + location +
                ", members=" + members +
                ", coreActivities=" + coreActivities +
                '}';
    }
}
