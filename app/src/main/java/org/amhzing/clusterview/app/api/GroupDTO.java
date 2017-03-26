package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.amhzing.clusterview.app.api.statistic.CoreActivitiesDTO;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class GroupDTO extends ResourceSupport {

    @JsonProperty("id")
    public final String id;
    @JsonProperty("members")
    public final List<MemberDTO> members;
    @JsonProperty("location")
    public final LocationDTO location;
    @JsonUnwrapped
    public final CoreActivitiesDTO coreActivities;

    @JsonCreator
    public GroupDTO(final String id, final List<MemberDTO> members, final LocationDTO location, final CoreActivitiesDTO coreActivities) {
        this.id = notBlank(id);
        this.members = noNullElements(members);
        this.location = notNull(location);
        this.coreActivities = notNull(coreActivities);
    }
}
