package org.amhzing.clusterview.domain;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.statistic.CoreActivity;

import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public final class Group {

    private final Id id;
    private final Set<Member> members;
    private final Location location;
    private final Set<CoreActivity> coreActivities;

    private Group(final Id id, final Set<Member> members, final Location location, final Set<CoreActivity> coreActivities) {
        noNullElements(members);
        noNullElements(coreActivities);

        this.id = notNull(id);
        this.members = ImmutableSet.copyOf(members);
        this.location = notNull(location);
        this.coreActivities = ImmutableSet.copyOf(coreActivities);
    }

    public static Group create(final Id id, final Set<Member> members, final Location location, final Set<CoreActivity> coreActivities) {
        return new Group(id, members, location, coreActivities);
    }

    public static Group empty(final Id id) {
        notNull(id);
        return create(id, emptySet(), nonExistingLocation(), emptySet());
    }

    public Id getId() {
        return id;
    }

    public Set<Member> getMembers() {
        return ImmutableSet.copyOf(members);
    }

    public Location getLocation() {
        return location;
    }

    public Set<CoreActivity> getCoreActivities() {
        return ImmutableSet.copyOf(coreActivities);
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
    private static Location nonExistingLocation() {
        return ImmutableLocation.of(-1, -1);
    }
}
