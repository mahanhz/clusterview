package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;

import java.util.List;

public interface StatisticHistoryRepository {

    List<DatedActivityStatistic> history(Cluster.Id clusterId);

    StatsHistoryEntity saveHistory(Cluster.Id clusterId, DatedActivityStatistic datedActivityStatistic);
}
