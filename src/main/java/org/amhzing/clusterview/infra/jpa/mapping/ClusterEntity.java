package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "cluster")
public final class ClusterEntity {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamEntity> teams;

    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getAreaCoords() {
        return areaCoords;
    }

    public void setAreaCoords(final String areaCoords) {
        this.areaCoords = areaCoords;
    }

    public Set<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(final Set<TeamEntity> teams) {
        this.teams = teams;
    }

    public RegionEntity getRegion() {
        return region;
    }

    public void setRegion(final RegionEntity region) {
        this.region = region;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ClusterEntity that = (ClusterEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(areaCoords, that.areaCoords) &&
                Objects.equals(teams, that.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaCoords, teams);
    }

    @Override
    public String toString() {
        return "ClusterEntity{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", teams=" + teams +
                '}';
    }
}
