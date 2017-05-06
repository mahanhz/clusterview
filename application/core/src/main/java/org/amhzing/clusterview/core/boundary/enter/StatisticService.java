package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;

public interface StatisticService<T> {

    T statistics(Country.Id countryId);

    T statistics(Region.Id regionId);

    T statistics(Cluster.Id clusterId);
}
