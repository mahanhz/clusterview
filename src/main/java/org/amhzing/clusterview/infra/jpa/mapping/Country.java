package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Country {

    @Id
    private String id;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Region> regions;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(final Set<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id='" + id + '\'' +
                ", regions=" + regions +
                '}';
    }
}
