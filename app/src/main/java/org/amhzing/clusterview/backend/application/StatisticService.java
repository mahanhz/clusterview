package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;
import org.amhzing.clusterview.backend.domain.model.Region;

public interface StatisticService {

    ActivityStatistic statistics(Country.Id country);

    ActivityStatistic statistics(Region.Id regionId);

    ActivityStatistic statistics(Cluster.Id clusterId);
}
