package org.amhzing.clusterview.web.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupModel {

    private long id;

    @NotEmpty(message = "Group must have at least one member") @Valid
    private List<MemberModel> members;

    @NotNull @Valid
    private LocationModel location;

    private List<CoreActivityModel> coreActivities;

    public GroupModel() {
        members = new ArrayList<>();
        location = new LocationModel();
        coreActivities = new ArrayList<>();
    }

    private GroupModel(final long id, final List<MemberModel> members, final LocationModel location, final List<CoreActivityModel> coreActivities) {
        this.id = id;
        this.members = noNullElements(members);
        this.location = notNull(location);
        this.coreActivities = noNullElements(coreActivities);
    }

    @Deprecated
    private GroupModel(final long id, final List<MemberModel> members, final LocationModel location) {
        this.id = id;
        this.members = noNullElements(members);
        this.location = notNull(location);
    }

    @Deprecated
    public static GroupModel create(final long id, final List<MemberModel> members, final LocationModel location) {
        return new GroupModel(id, members, location);
    }

    public static GroupModel create(final long id, final List<MemberModel> members, final LocationModel location, final List<CoreActivityModel> coreActivities) {
        return new GroupModel(id, members, location, coreActivities);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public List<MemberModel> getMembers() {
        return members;
    }

    public void setMembers(final List<MemberModel> members) {
        this.members = members;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(final LocationModel location) {
        this.location = location;
    }

    public List<CoreActivityModel> getCoreActivities() {
        return coreActivities;
    }

    public void setCoreActivities(final List<CoreActivityModel> coreActivities) {
        this.coreActivities = coreActivities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupModel that = (GroupModel) o;
        return id == that.id &&
                Objects.equals(members, that.members) &&
                Objects.equals(location, that.location) &&
                Objects.equals(coreActivities, that.coreActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, location, coreActivities);
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", members=" + members +
                ", location=" + location +
                ", coreActivities=" + coreActivities +
                '}';
    }
}
