package org.amhzing.clusterview.backend.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class CoreActivityModel {

    private String id;
    private String name;
    private long quantity;
    private long totalParticipants;
    private long communityOfInterest;

    public CoreActivityModel() {
    }

    private CoreActivityModel(final String id, final String name, final long quantity, final long totalParticipants, final long communityOfInterest) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.quantity = quantity;
        this.totalParticipants = totalParticipants;
        this.communityOfInterest = communityOfInterest;
    }

    public static CoreActivityModel create(final String id, final String name, final long quantity, final long totalParticipants, final long communityOfInterest) {
        return new CoreActivityModel(id, name, quantity, totalParticipants, communityOfInterest);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public long getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(final long totalParticipants) {
        this.totalParticipants = totalParticipants;
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
        if (!(o instanceof CoreActivityModel)) return false;
        final CoreActivityModel that = (CoreActivityModel) o;
        return quantity == that.quantity &&
                totalParticipants == that.totalParticipants &&
                communityOfInterest == that.communityOfInterest &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, totalParticipants, communityOfInterest);
    }

    @Override
    public String toString() {
        return "CoreActivityModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", totalParticipants=" + totalParticipants +
                ", communityOfInterest=" + communityOfInterest +
                '}';
    }
}
