package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;

import java.util.List;

public interface RegionService {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id region);
}
