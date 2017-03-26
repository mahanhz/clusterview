package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;

import java.util.List;

public interface RegionService {

    List<Region.Id> regions(Country.Id id);

    List<Cluster.Id> clusters(Region.Id region);
}
