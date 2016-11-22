package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.ClusterService;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.amhzing.clusterview.web.adapter.AdapterFactory.convertGroups;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ClusterAdapter {

    private ClusterService clusterService;

    @Autowired
    public ClusterAdapter(final ClusterService clusterService) {
        this.clusterService = notNull(clusterService);
    }

    public Set<GroupModel> groups(final String cluster) {
        final Set<Group> groups = clusterService.groups(cluster);

        return convertGroups(groups);
    }
}
