package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;

import java.util.List;

public interface RegionService {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id region);
}
