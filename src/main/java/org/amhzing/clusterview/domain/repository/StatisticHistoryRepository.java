package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    DatedActivityStatistic saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
