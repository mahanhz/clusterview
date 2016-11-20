package org.amhzing.clusterview.domain.model;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.*;

public class Country {

    private static final int MAX_NAME_LENGTH = 100;

    private Id id;
    private String name;
    private Set<Region.Id> regions;

    private Country(final Id id, final String name, final Set<Region.Id> regions) {
        isValidName(name);

        this.id = notNull(id);
        this.name = trim(name);
        this.regions = noNullElements(regions);
    }

    public static Country create(final Id id, final String name, final Set<Region.Id> regions) {
        return new Country(id, name, regions);
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Region.Id> getRegions() {
        return regions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(regions, country.regions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, regions);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regions=" + regions +
                '}';
    }

    private boolean isValidName(final String value) {
        isNotBlank(value);
        isTrue(trim(value).length() <= MAX_NAME_LENGTH);

        return true;
    }

    public static class Id {

        private String id;

        private Id(final String id) {
            this.id = notBlank(id);
        }

        public static Id create(final String id) {
            return new Id(id);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}