package org.amhzing.clusterview.domain.model.statistic;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class CoreActivity {

    private Id id;
    private String name;
    private Quantity totalParticipants;
    private Quantity communityOfInterest;

    private CoreActivity(final Id id, final String name, final Quantity totalParticipants, final Quantity communityOfInterest) {
        this.id = notNull(id);
        this.name = notBlank(name);
        this.totalParticipants = notNull(totalParticipants);
        this.communityOfInterest = notNull(communityOfInterest);
    }

    public static CoreActivity create(final Id id, final String name, final Quantity totalParticipants, final Quantity communityOfInterest) {
        return new CoreActivity(id, name, totalParticipants, communityOfInterest);
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Quantity getTotalParticipants() {
        return totalParticipants;
    }

    public Quantity getCommunityOfInterest() {
        return communityOfInterest;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CoreActivity activity = (CoreActivity) o;
        return Objects.equals(id, activity.id) &&
                Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CoreActivity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalParticipants=" + totalParticipants +
                ", communityOfInterest=" + communityOfInterest +
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
