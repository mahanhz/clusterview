package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.RegionRepository;
import org.amhzing.clusterview.data.jpa.entity.CountryEntity;
import org.amhzing.clusterview.data.jpa.repository.CountryJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.RegionJpaRepository;
import org.amhzing.clusterview.data.jpa.entity.RegionEntity;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.data.cache.CacheSpec.DEFAULT_CACHE_KEY;
import static org.amhzing.clusterview.data.cache.CacheSpec.REGIONS_CACHE_NAME;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = REGIONS_CACHE_NAME)
public class DefaultRegionRepository implements RegionRepository {

    private CountryJpaRepository countryJpaRepository;
    private RegionJpaRepository regionJpaRepository;

    public DefaultRegionRepository(final CountryJpaRepository countryJpaRepository,
                                   final RegionJpaRepository regionJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
        this.regionJpaRepository = notNull(regionJpaRepository);
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null or #result.isEmpty()")
    public List<Region.Id> regions(final Country.Id id) {
        notNull(id);

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        if (country == null) {
            return emptyList();
        }

        return country.getRegions()
                      .stream()
                      .map(region -> Region.Id.create(region.getId()))
                      .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key= DEFAULT_CACHE_KEY, unless = "#result == null or #result.isEmpty()")
    public List<Cluster.Id> clusters(final Region.Id id) {
        notNull(id);

        final RegionEntity region = regionJpaRepository.findOne(id.getId());

        if (region == null) {
            return emptyList();
        }

        return region.getClusters()
                     .stream()
                     .map(cluster -> Cluster.Id.create(cluster.getId()))
                     .collect(Collectors.toList());
    }
}
