package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;

public interface StatisticService {

    ActivityStatistic statistics(Country.Id country);

    ActivityStatistic statistics(Region.Id regionId);

    ActivityStatistic statistics(Cluster.Id clusterId);
}