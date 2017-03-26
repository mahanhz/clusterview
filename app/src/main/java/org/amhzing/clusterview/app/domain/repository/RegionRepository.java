package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;

import java.util.List;

public interface RegionRepository {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id id);
}
