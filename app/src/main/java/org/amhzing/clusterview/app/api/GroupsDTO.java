package org.amhzing.clusterview.app.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class GroupsDTO extends ResourceSupport {

    @JsonProperty("groups")
    public final List<GroupDTO> groups;

    @JsonCreator
    public GroupsDTO(final List<GroupDTO> groups) {
        this.groups = noNullElements(groups);
    }
}
