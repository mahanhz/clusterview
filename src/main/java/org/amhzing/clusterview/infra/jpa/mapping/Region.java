package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Region {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private Set<Cluster> clusters;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

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

    public Set<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(final Set<Cluster> clusters) {
        this.clusters = clusters;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", clusters=" + clusters +
                '}';
    }
}
