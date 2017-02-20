package org.amhzing.clusterview.backend.infra.jpa.mapping;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "region")
public final class RegionEntity extends BaseEntity {

    @Id
    private String id;

    private String areaCoords;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RegionEntity that = (RegionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(areaCoords, that.areaCoords) &&
                Objects.equals(clusters, that.clusters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaCoords, clusters);
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
