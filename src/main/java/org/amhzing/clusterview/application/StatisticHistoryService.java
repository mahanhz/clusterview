package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryService {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    void saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
