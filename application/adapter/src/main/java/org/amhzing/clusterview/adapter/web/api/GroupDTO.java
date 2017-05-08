package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.amhzing.clusterview.adapter.web.api.statistic.CoreActivityDTO;

import java.util.List;

import static org.apache.commons.lang3.Validate.*;

public class GroupDTO  {

    @JsonProperty("id")
    public final String id;
    @JsonProperty("members")
    public final List<MemberDTO> members;
    @JsonProperty("location")
    public final LocationDTO location;
    @JsonProperty("coreActivities")
    public final List<CoreActivityDTO> coreActivities;

    @JsonCreator
    public GroupDTO(@JsonProperty("id") final String id,
                    @JsonProperty("members") final List<MemberDTO> members,
                    @JsonProperty("location") final LocationDTO location,
                    @JsonProperty("coreActivities") final List<CoreActivityDTO> coreActivities) {
        this.id = notNull(id);
        this.members = notEmpty(members, "Group must have at least one member");
        this.location = notNull(location);
        this.coreActivities = noNullElements(coreActivities);
    }
}
