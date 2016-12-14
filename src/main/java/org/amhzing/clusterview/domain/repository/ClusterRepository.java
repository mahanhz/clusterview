package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;

import java.util.List;

public interface ClusterRepository {

    List<Cluster.Id> clusters(Country.Id id);
}
