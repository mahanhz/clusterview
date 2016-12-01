package org.amhzing.clusterview.infra.jpa.mapping;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ParticipantQuantity {

    private long total;
    private long communityOfInterest;

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
        if (o == null || getClass() != o.getClass()) return false;
        final ParticipantQuantity that = (ParticipantQuantity) o;
        return total == that.total &&
                communityOfInterest == that.communityOfInterest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, communityOfInterest);
    }

    @Override
    public String toString() {
        return "ParticipantQuantity{" +
                "total=" + total +
                ", communityOfInterest=" + communityOfInterest +
                '}';
    }
}
