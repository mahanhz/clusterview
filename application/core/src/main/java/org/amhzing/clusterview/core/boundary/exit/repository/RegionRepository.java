package org.amhzing.clusterview.core.boundary.exit.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;

import java.util.List;

public interface RegionRepository {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id id);
}
