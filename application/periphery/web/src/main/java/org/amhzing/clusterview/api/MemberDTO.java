package org.amhzing.clusterview.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class MemberDTO {

    @JsonProperty("name")
    public final NameDTO name;
    @JsonProperty("capabilities")
    public final List<ReferenceActivityDTO> capabilities;
    @JsonProperty("commitments")
    public final List<ReferenceActivityDTO> commitments;

    @JsonCreator
    public MemberDTO(@JsonProperty("name") final NameDTO name,
                     @JsonProperty("capabilities") final List<ReferenceActivityDTO> capabilities,
                     @JsonProperty("commitments") final List<ReferenceActivityDTO> commitments) {
        this.name = notNull(name);
        this.capabilities = notNull(capabilities);
        this.commitments = notNull(commitments);
    }
}
