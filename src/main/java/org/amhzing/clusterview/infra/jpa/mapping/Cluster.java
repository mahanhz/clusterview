package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Cluster {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL)
    private Set<Team> teams;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

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

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(final Set<Team> teams) {
        this.teams = teams;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(final Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", teams=" + teams +
                '}';
    }
}
