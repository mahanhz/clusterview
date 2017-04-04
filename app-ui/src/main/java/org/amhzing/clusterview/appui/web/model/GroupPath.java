package org.amhzing.clusterview.appui.web.model;

import org.springframework.http.HttpMethod;

public class GroupPath extends ClusterPath {

    public static final String CREATE_GROUP = "creategroup";

    private String obfuscatedGroupId;
    private String action = CREATE_GROUP;  // default to create a new group
    private String method = HttpMethod.POST.name(); // default to post


    public String getObfuscatedGroupId() {
        return obfuscatedGroupId;
    }

    public void setObfuscatedGroupId(final String obfuscatedGroupId) {
        this.obfuscatedGroupId = obfuscatedGroupId;
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
                "obfuscatedGroupId='" + obfuscatedGroupId + '\'' +
                ", action='" + action + '\'' +
                ", method='" + method + '\'' +
                "} " + super.toString();
    }
}
