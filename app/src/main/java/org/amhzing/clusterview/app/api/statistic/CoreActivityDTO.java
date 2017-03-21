package org.amhzing.clusterview.app.api.statistic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class CoreActivityDTO {

    @JsonProperty("id")
    public final String id;
    @JsonProperty("name")
    public final String name;
    @JsonProperty("quantity")
    public final int quantity;
    @JsonProperty("totalParticipants")
    public final int totalParticipants;
    @JsonProperty("communityOfInterest")
    public final int communityOfInterest;

    @JsonCreator
    public CoreActivityDTO(final String id,
                           final String name,
                           final int quantity,
                           final int totalParticipants,
                           final int communityOfInterest) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
        this.totalParticipants = totalParticipants;
        this.communityOfInterest = communityOfInterest;
    }
}
