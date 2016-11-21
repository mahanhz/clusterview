package org.amhzing.clusterview.web.model;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class GroupModel {

    private long id;
    private Set<MemberModel> members;
    private LocationModel location;

    private GroupModel(final long id, final Set<MemberModel> members, final LocationModel location) {
        this.id = id;
        this.members = noNullElements(members);
        this.location = notNull(location);
    }

    public static GroupModel create(final long id, final Set<MemberModel> members, final LocationModel location) {
        return new GroupModel(id, members, location);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Set<MemberModel> getMembers() {
        return members;
    }

    public void setMembers(final Set<MemberModel> members) {
        this.members = members;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(final LocationModel location) {
        this.location = location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupModel that = (GroupModel) o;
        return id == that.id &&
                Objects.equals(members, that.members) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, location);
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", members=" + members +
                ", location=" + location +
                '}';
    }
}
