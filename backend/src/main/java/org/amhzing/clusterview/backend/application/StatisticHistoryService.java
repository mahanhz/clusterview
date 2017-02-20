package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;

import java.util.List;

public interface StatisticHistoryService {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    void saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
