package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Group;

import java.util.Set;

public interface ClusterService {

    Set<Group> groups(final String cluster);
}
