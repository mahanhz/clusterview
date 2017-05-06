package org.amhzing.clusterview.api.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class CacheDTO extends ResourceSupport {

    @JsonProperty("cacheNames")
    public final List<String> cacheNames;

    @JsonCreator
    public CacheDTO(@JsonProperty("cacheNames") final List<String> cacheNames) {
        this.cacheNames = notNull(cacheNames);
    }
}
