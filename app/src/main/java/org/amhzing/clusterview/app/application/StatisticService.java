package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;

public interface StatisticService<T> {

    T statistics(Country.Id countryId);

    T statistics(Region.Id regionId);

    T statistics(Cluster.Id clusterId);
}
