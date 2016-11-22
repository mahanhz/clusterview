package org.amhzing.clusterview.web.model;

public class GroupPath extends ClusterPath {

    private long groupId;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "GroupPath{" +
                "groupId=" + groupId +
                '}';
    }
}
