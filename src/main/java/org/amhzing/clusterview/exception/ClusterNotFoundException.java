package org.amhzing.clusterview.exception;

import static org.apache.commons.lang3.Validate.notBlank;

public class ClusterNotFoundException extends RuntimeException {

    private String cluster;

    public ClusterNotFoundException(final String message, final String cluster) {
        super(message);
        this.cluster = notBlank(cluster);
    }

    public ClusterNotFoundException(final String message, final Throwable cause, final String cluster) {
        super(message, cause);
        this.cluster = notBlank(cluster);
    }

    public String getCluster() {
        return cluster;
    }
}
