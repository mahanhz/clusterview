package org.amhzing.clusterview.adapter.web.util;

import org.amhzing.clusterview.adapter.web.api.ClusterDTO;
import org.amhzing.clusterview.adapter.web.api.ClustersDTO;
import org.amhzing.clusterview.core.domain.Cluster;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public final class ClusterDtoFactory {

    private ClusterDtoFactory() {
        // To prevent instantiation
    }

    public static ClustersDTO clustersDTO(final List<Cluster.Id> clusters) {
        final List<ClusterDTO> clustersDto = clusters.stream()
                                                     .map(cluster -> new ClusterDTO(cluster.getId()))
                                                     .sorted(comparing(cluster -> cluster.name))
                                                     .collect(toList());

        return new ClustersDTO(clustersDto);
    }
}
