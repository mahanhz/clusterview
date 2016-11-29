package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.infra.repository.CountryStatisticRepository;

import static org.apache.commons.lang3.Validate.notNull;

public class CountryStatisticService implements StatisticService<Country.Id> {

    private CountryStatisticRepository statisticRepository;

    public CountryStatisticService(final CountryStatisticRepository statisticRepository) {
        this.statisticRepository = notNull(statisticRepository);
    }

    @Override
    public ActivityStatistic statistics(final Country.Id id) {

        return statisticRepository.statistics(id);
    }
}
