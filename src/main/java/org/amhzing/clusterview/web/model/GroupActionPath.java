package org.amhzing.clusterview.web.model;

public class GroupActionPath extends ClusterPath {

    private String groupAction;

    public String getGroupAction() {
        return groupAction;
    }

    public void setGroupAction(final String groupAction) {
        this.groupAction = groupAction;
    }

    @Override
    public String toString() {
        return "GroupActionPath{" +
                "groupAction='" + groupAction + '\'' +
                '}';
    }
}
