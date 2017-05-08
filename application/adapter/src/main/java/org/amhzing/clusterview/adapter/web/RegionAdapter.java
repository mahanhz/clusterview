package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.RegionDTO;
import org.amhzing.clusterview.core.boundary.enter.RegionService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class RegionAdapter {

    private RegionService regionService;

    public RegionAdapter(final RegionService regionService) {
        this.regionService = notNull(regionService);
    }

    public List<RegionDTO> regions(final String country) {
        notNull(country);

        final List<Region.Id> regions = regionService.regions(Country.Id.create(country));

        return regions.stream()
                      .map(region -> new RegionDTO(region.getId()))
                      .collect(toList());
    }

    public List<ClusterDTO> clusters(final String region) {
        notNull(region);

        final List<Cluster.Id> clusters = regionService.clusters(Region.Id.create(region));

        return clusters.stream()
                      .map(cluster -> new ClusterDTO(cluster.getId()))
                      .collect(toList());
    }
}
