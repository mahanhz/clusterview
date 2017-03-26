package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class ClustersDTO extends ResourceSupport {

    @JsonProperty("clusters")
    public final List<ClusterDTO> clusters;

    public ClustersDTO(final List<ClusterDTO> clusters) {
        this.clusters = noNullElements(clusters);
    }
}
