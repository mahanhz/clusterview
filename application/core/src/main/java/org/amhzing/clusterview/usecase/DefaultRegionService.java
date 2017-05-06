package org.amhzing.clusterview.usecase;

import org.amhzing.clusterview.boundary.enter.RegionService;
import org.amhzing.clusterview.boundary.exit.repository.RegionRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultRegionService implements RegionService {

    private RegionRepository regionRepository;

    public DefaultRegionService(final RegionRepository regionRepository) {
        this.regionRepository = notNull(regionRepository);
    }

    @Override
    public List<Region.Id> regions(final Country.Id id) {
        notNull(id);

        return regionRepository.regions(id);
    }

    @Override
    public List<Cluster.Id> clusters(final Region.Id region) {
        notNull(region);

        return regionRepository.clusters(region);
    }
}
