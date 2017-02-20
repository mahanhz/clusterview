package org.amhzing.clusterview.backend.domain.repository;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;

import java.util.List;

public interface ClusterRepository {

    List<Cluster.Id> clusters(Country.Id id);
}
