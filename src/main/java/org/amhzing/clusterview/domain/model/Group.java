package org.amhzing.clusterview.domain.model;

import org.amhzing.clusterview.domain.model.statistic.CoreActivity;

import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class Group {

    private Id id;
    private Set<Member> members;
    private Location location;
    private Set<CoreActivity> coreActivities;

    public Group() {
    }

    private Group(final Id id, final Set<Member> members, final Location location, final Set<CoreActivity> coreActivities) {
        this.id = notNull(id);
        this.members = noNullElements(members);
        this.location = notNull(location);
        this.coreActivities = noNullElements(coreActivities);
    }

    public static Group create(final Id id, final Set<Member> members, final Location location, final Set<CoreActivity> coreActivities) {
        return new Group(id, members, location, coreActivities);
    }

    public Id getId() {
        return id;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Location getLocation() {
        return location;
    }

    public Set<CoreActivity> getCoreActivities() {
        return coreActivities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(members, group.members) &&
                Objects.equals(location, group.location) &&
                Objects.equals(coreActivities, group.coreActivities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, location, coreActivities);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", members=" + members +
                ", location=" + location +
                ", coreActivities=" + coreActivities +
                '}';
    }

    public static class Id {

        private long id;

        private Id(final long id) {
            this.id = id;
        }

        public static Id create(final long id) {
            return new Id(id);
        }

        public long getId() {
            return id;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Id id1 = (Id) o;
            return Objects.equals(id, id1.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "id='" + id + '\'' +
                    '}';
        }
    }
}
