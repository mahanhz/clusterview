package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.ClustersDTO;
import org.amhzing.clusterview.core.boundary.enter.ClusterService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class ClusterAdapter {

    private ClusterService clusterService;

    public ClusterAdapter(final ClusterService clusterService) {
        this.clusterService = notNull(clusterService);
    }

    public ClustersDTO clusters(final String userCountry) {
        notNull(userCountry);

        final List<Cluster.Id> clusters = clusterService.clusters(Country.Id.create(userCountry));

        final List<ClusterDTO> clustersDto = clusters.stream()
                                                     .map(cluster -> new ClusterDTO(cluster.getId()))
                                                     .sorted(comparing(cluster -> cluster.name))
                                                     .collect(toList());

        return new ClustersDTO(clustersDto);
    }
}
