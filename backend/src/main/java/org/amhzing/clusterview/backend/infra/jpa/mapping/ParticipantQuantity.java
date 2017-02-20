package org.amhzing.clusterview.backend.infra.jpa.mapping;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ParticipantQuantity {

    private long numActivities;
    private long total;
    private long communityOfInterest;

    public ParticipantQuantity() {
    }

    private ParticipantQuantity(final long numActivities, final long total, final long communityOfInterest) {
        this.numActivities = numActivities;
        this.total = total;
        this.communityOfInterest = communityOfInterest;
    }

    public static ParticipantQuantity create(final long numActivities, final long total, final long communityOfInterest) {
        return new ParticipantQuantity(numActivities, total, communityOfInterest);
    }

    public long getNumActivities() {
        return numActivities;
    }

    public void setNumActivities(final long numActivities) {
        this.numActivities = numActivities;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    public long getCommunityOfInterest() {
        return communityOfInterest;
    }

    public void setCommunityOfInterest(final long communityOfInterest) {
        this.communityOfInterest = communityOfInterest;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantQuantity)) return false;
        final ParticipantQuantity that = (ParticipantQuantity) o;
        return numActivities == that.numActivities &&
                total == that.total &&
                communityOfInterest == that.communityOfInterest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numActivities, total, communityOfInterest);
    }

    @Override
    public String toString() {
        return "ParticipantQuantity{" +
                "numActivities=" + numActivities +
                ", total=" + total +
                ", communityOfInterest=" + communityOfInterest +
                '}';
    }
}
