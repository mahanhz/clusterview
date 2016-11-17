package org.amhzing.clusterview.domain.model;

import com.google.common.collect.ImmutableSet;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class Group {

    private Set<Member> members;
    private Location location;

    private Group(final Set<Member> members, final Location location) {
        this.members = noNullElements(members);
        this.location = notNull(location);
    }

    public static Group create(final Set<Member> members, final Location location) {
        return new Group(members, location);
    }

    public Set<Member> getMembers() {
        return ImmutableSet.copyOf(members);
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Group group = (Group) o;
        return Objects.equals(members, group.members) &&
                Objects.equals(location, group.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members, location);
    }

    @Override
    public String toString() {
        return "Group{" +
                "members=" + members +
                ", location=" + location +
                '}';
    }
}
