package org.amhzing.clusterview.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

public class CacheNamesModel {

    @NotEmpty(message = "Must specify at least one cache")
    private List<String> cacheNames;

    public List<String> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(final List<String> cacheNames) {
        this.cacheNames = cacheNames;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheNamesModel)) return false;
        final CacheNamesModel that = (CacheNamesModel) o;
        return Objects.equals(cacheNames, that.cacheNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cacheNames);
    }

    @Override
    public String toString() {
        return "CacheNamesModel{" +
                "cacheNames=" + cacheNames +
                '}';
    }
}
