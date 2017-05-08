package org.amhzing.clusterview.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;

public class GroupsDTO {

    @JsonProperty("groups")
    public final List<GroupDTO> groups;

    public GroupsDTO(final List<GroupDTO> groups) {
        this.groups = noNullElements(groups);
    }
}
