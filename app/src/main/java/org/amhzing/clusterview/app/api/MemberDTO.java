package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import static org.apache.commons.lang3.Validate.notNull;

public class MemberDTO {

    @JsonProperty("name")
    public final NameDTO name;
    @JsonUnwrapped
    public final CapabilitiesDTO capabilities;
    @JsonUnwrapped
    public final CommitmentsDTO commitments;

    @JsonCreator
    public MemberDTO(final NameDTO name, final CapabilitiesDTO capabilities, final CommitmentsDTO commitments) {
        this.name = notNull(name);
        this.capabilities = notNull(capabilities);
        this.commitments = notNull(commitments);
    }
}
