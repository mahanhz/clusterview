package org.amhzing.clusterview.core.boundary.exit.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    DatedActivityStatistic saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
