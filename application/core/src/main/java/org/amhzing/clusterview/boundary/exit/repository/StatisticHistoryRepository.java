package org.amhzing.clusterview.boundary.exit.repository;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    DatedActivityStatistic saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
