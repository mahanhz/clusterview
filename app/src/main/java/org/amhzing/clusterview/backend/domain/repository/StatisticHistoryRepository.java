package org.amhzing.clusterview.backend.domain.repository;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    DatedActivityStatistic saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
