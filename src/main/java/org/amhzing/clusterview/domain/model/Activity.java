package org.amhzing.clusterview.domain.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class Activity {

    private Id id;
    private String name;

    private Activity(final Id id, final String name) {
        this.id = notNull(id);
        this.name = notBlank(name);
    }

    public static Activity create(final Id id, final String name) {
        return new Activity(id, name);
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) &&
                Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Id {

        private String id;

        private Id(final String id) {
            this.id = notBlank(id);
        }

        public static Id create(final String id) {
            return new Id(id);
        }

        public String getId() {
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
