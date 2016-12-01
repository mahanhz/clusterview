package org.amhzing.clusterview.web.model;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notBlank;

public final class CoreActivityModel {

    private String id;
    private String name;
    private long totalParticipants;
    private long communityOfInterest;

    public CoreActivityModel() {
    }

    private CoreActivityModel(final String id, final String name, final long totalParticipants, final long communityOfInterest) {
        this.id = notBlank(id);
        this.name = notBlank(name);
        this.totalParticipants = totalParticipants;
        this.communityOfInterest = communityOfInterest;
    }

    public static CoreActivityModel create(final String id, final String name, final long totalParticipants, final long communityOfInterest) {
        return new CoreActivityModel(id, name, totalParticipants, communityOfInterest);
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
        if (o == null || getClass() != o.getClass()) return false;
        final CoreActivityModel that = (CoreActivityModel) o;
        return totalParticipants == that.totalParticipants &&
                communityOfInterest == that.communityOfInterest &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, totalParticipants, communityOfInterest);
    }

    @Override
    public String toString() {
        return "CoreActivityModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalParticipants=" + totalParticipants +
                ", communityOfInterest=" + communityOfInterest +
                '}';
    }
}
