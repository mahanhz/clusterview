package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;

import java.util.List;

public interface StatisticHistoryService {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    void saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
