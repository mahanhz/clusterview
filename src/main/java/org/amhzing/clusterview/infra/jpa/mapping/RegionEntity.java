package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
public class RegionEntity {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private Set<ClusterEntity> clusters;

    @ManyToOne(fetch = FetchType.LAZY)
    private CountryEntity country;

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

    public Set<ClusterEntity> getClusters() {
        return clusters;
    }

    public void setClusters(final Set<ClusterEntity> clusters) {
        this.clusters = clusters;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "RegionEntity{" +
                "id='" + id + '\'' +
                ", areaCoords='" + areaCoords + '\'' +
                ", clusters=" + clusters +
                '}';
    }
}
