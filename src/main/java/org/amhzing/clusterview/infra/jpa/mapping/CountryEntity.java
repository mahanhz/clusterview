package org.amhzing.clusterview.infra.jpa.mapping;

import org.amhzing.clusterview.infra.jpa.mapping.user.UserEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity(name = "country")
public final class CountryEntity extends BaseEntity {

    @Id
    private String id;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RegionEntity> regions;

    @ManyToMany(mappedBy = "countries")
    private Collection<UserEntity> users;

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

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(final Collection<UserEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryEntity)) return false;
        final CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(regions, that.regions) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regions, users);
    }

    @Override
    public String toString() {
        return "CountryEntity{" +
                "id='" + id + '\'' +
                ", regions=" + regions +
                ", users=" + users +
                '}';
    }
}
