package org.amhzing.clusterview.web.model;

import org.springframework.http.HttpMethod;

public class GroupPath extends ClusterPath {

    public static final String CREATE_GROUP = "creategroup";

    private long groupId;
    private String action = CREATE_GROUP;  // default to create a new group
    private String method = HttpMethod.POST.name(); // default to post

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "GroupPath{" +
                "groupId=" + groupId +
                ", action='" + action + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
