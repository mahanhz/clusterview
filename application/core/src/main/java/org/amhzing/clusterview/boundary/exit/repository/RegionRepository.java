package org.amhzing.clusterview.boundary.exit.repository;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;

import java.util.List;

public interface RegionRepository {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id id);
}
