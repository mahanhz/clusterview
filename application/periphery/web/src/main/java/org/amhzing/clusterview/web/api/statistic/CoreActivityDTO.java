package org.amhzing.clusterview.web.api.statistic;

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
    public CoreActivityDTO(@JsonProperty("id") final String id,
                           @JsonProperty("name") final String name,
                           @JsonProperty("quantity") final int quantity,
                           @JsonProperty("totalParticipants") final int totalParticipants,
                           @JsonProperty("communityOfInterest") final int communityOfInterest) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
        this.totalParticipants = totalParticipants;
        this.communityOfInterest = communityOfInterest;
    }
}
