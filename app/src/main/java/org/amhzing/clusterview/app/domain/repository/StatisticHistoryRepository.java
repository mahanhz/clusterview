package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    DatedActivityStatistic saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
