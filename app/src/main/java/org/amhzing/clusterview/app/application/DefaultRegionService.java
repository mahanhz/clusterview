package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.repository.RegionRepository;

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
