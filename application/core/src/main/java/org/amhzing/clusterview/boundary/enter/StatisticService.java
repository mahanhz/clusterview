package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.Region;

public interface StatisticService<T> {

    T statistics(Country.Id countryId);

    T statistics(Region.Id regionId);

    T statistics(Cluster.Id clusterId);
}
