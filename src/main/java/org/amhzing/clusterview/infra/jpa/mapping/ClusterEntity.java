package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "cluster")
public class ClusterEntity {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
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
    public String toString() {
        return "ClusterEntity{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", teams=" + teams +
                '}';
    }
}
