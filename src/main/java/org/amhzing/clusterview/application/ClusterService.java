package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;

import java.util.List;

public interface ClusterService {

    List<Cluster.Id> clusters(Country.Id country);
}
