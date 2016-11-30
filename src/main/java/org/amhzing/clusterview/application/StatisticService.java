package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;

public interface StatisticService {

    ActivityStatistic statistics(Country.Id country);

    ActivityStatistic statistics(Region.Id regionId);

    ActivityStatistic statistics(Cluster.Id clusterId);
}
