package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;

import java.util.List;

public interface ClusterService {

    List<Cluster.Id> clusters(Country.Id country);
}
