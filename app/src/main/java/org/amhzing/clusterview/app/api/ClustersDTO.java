package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ClustersDTO {

    @JsonProperty("clusters")
    public final List<ClusterDTO> clusters;

    @JsonCreator
    public ClustersDTO(final List<ClusterDTO> clusters) {
        this.clusters = noNullElements(clusters);
    }
}
