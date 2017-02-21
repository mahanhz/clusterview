package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;

import java.util.List;

public interface ClusterService {

    List<Cluster.Id> clusters(Country.Id country);
}
