package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class RegionStatisticService implements StatisticService<Region.Id> {

    private RegionStatisticRepository statisticRepository;

    public RegionStatisticService(final RegionStatisticRepository statisticRepository) {
        this.statisticRepository = notNull(statisticRepository);
    }

    @Override
    public ActivityStatistic statistics(final Region.Id id) {

        return statisticRepository.statistics(id);
    }
}
