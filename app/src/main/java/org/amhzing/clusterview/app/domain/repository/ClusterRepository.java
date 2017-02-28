package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;

import java.util.List;

public interface ClusterRepository {

    List<Cluster.Id> clusters(Country.Id id);
}