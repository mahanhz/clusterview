package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity(name = "country")
public class CountryEntity {

    @Id
    private String id;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegionEntity> regions;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Set<RegionEntity> getRegions() {
        return regions;
    }

    public void setRegions(final Set<RegionEntity> regions) {
        this.regions = regions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(regions, that.regions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regions);
    }

    @Override
    public String toString() {
        return "CountryEntity{" +
                "id='" + id + '\'' +
                ", regions=" + regions +
                '}';
    }
}
