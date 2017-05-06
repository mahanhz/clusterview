package org.amhzing.clusterview.data.jpa.entity.stats;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class CoreActivityStats {

    private int quantity;
    private int totalParticipants;
    private int coi;

    public CoreActivityStats() {
    }

    private CoreActivityStats(final int quantity, final int totalParticipants, final int coi) {
        this.quantity = quantity;
        this.totalParticipants = totalParticipants;
        this.coi = coi;
    }

    public static CoreActivityStats create(final int quantity, final int totalParticipants, final int coi) {
        return new CoreActivityStats(quantity, totalParticipants, coi);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(final int totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public int getCoi() {
        return coi;
    }

    public void setCoi(final int coi) {
        this.coi = coi;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CoreActivityStats)) return false;
        final CoreActivityStats that = (CoreActivityStats) o;
        return quantity == that.quantity &&
                totalParticipants == that.totalParticipants &&
                coi == that.coi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, totalParticipants, coi);
    }

    @Override
    public String toString() {
        return "CoreActivityStats{" +
                "quantity=" + quantity +
                ", totalParticipants=" + totalParticipants +
                ", coi=" + coi +
                '}';
    }
}
