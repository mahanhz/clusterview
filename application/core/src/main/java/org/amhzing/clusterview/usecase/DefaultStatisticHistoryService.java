package org.amhzing.clusterview.usecase;

import org.amhzing.clusterview.boundary.enter.StatisticHistoryService;
import org.amhzing.clusterview.boundary.exit.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.DatedActivityStatistic;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultStatisticHistoryService implements StatisticHistoryService {

    private StatisticHistoryRepository statisticHistoryRepository;

    public DefaultStatisticHistoryService(final StatisticHistoryRepository statisticHistoryRepository) {
        this.statisticHistoryRepository = notNull(statisticHistoryRepository);
    }

    @Override
    public List<DatedActivityStatistic> history(final Cluster.Id clusterId) {
        notNull(clusterId);

        return statisticHistoryRepository.history(clusterId);
    }

    @Override
    public void saveHistory(final Cluster.Id clusterId, final ActivityStatistic activityStatistic) {
        notNull(clusterId);
        notNull(activityStatistic);

        statisticHistoryRepository.saveHistory(clusterId, activityStatistic);
    }
}
