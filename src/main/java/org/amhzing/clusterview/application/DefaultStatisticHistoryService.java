package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.repository.StatisticHistoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    public void saveHistory(final Cluster.Id clusterId, final ActivityStatistic activityStatistic) {
        notNull(clusterId);
        notNull(activityStatistic);

        statisticHistoryRepository.saveHistory(clusterId, activityStatistic);
    }
}
