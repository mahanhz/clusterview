package org.amhzing.clusterview.exception;

import static org.apache.commons.lang3.Validate.notBlank;

public class GroupNotFoundException extends RuntimeException {

    private String groupId;
    private String cluster;

    public GroupNotFoundException(final String message, final String cluster, final String groupId) {
        super(message);
        this.cluster = notBlank(cluster);
        this.groupId = notBlank(groupId);
    }

    public GroupNotFoundException(final String message, final Throwable cause, final String cluster, final String groupId) {
        super(message, cause);
        this.cluster = notBlank(cluster);
        this.groupId = notBlank(groupId);
    }

    public String getCluster() {
        return cluster;
    }

    public String getGroupId() {
        return groupId;
    }
}
