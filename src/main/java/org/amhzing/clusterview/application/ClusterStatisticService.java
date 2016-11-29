package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class ClusterStatisticService implements StatisticService<Cluster.Id> {

    private ClusterStatisticRepository statisticRepository;

    public ClusterStatisticService(final ClusterStatisticRepository statisticRepository) {
        this.statisticRepository = notNull(statisticRepository);
    }

    @Override
    public ActivityStatistic statistics(final Cluster.Id id) {

        return statisticRepository.statistics(id);
    }
}
