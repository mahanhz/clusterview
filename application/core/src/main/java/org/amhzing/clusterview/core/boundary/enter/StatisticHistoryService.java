package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.DatedActivityStatistic;

import java.util.List;

public interface StatisticHistoryService {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    void saveHistory(Cluster.Id clusterId, ActivityStatistic activityStatistic);
}
