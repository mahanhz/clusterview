package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryService {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    void saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
