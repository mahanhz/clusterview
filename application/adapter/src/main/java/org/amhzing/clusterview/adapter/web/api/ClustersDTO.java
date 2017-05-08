package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ClustersDTO {

    @JsonProperty("clusters")
    public final List<ClusterDTO> clusters;

    public ClustersDTO(final List<ClusterDTO> clusters) {
        this.clusters = noNullElements(clusters);
    }
}
