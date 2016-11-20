package org.amhzing.clusterview.web.model;

import org.hibernate.validator.constraints.NotBlank;

public class Cluster extends Region {

    @NotBlank
    private String cluster;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(final String cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "cluster='" + cluster + '\'' +
                '}';
    }
}
