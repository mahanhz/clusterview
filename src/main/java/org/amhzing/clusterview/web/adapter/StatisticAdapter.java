package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.ClusterStatisticService;
import org.amhzing.clusterview.application.CountryStatisticService;
import org.amhzing.clusterview.application.RegionStatisticService;
import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class StatisticAdapter {

    private CountryStatisticService countryStatisticService;
    private RegionStatisticService regionStatisticService;
    private ClusterStatisticService clusterStatisticService;

    @Autowired
    public StatisticAdapter(final CountryStatisticService countryStatisticService,
                            final RegionStatisticService regionStatisticService,
                            final ClusterStatisticService clusterStatisticService) {
        this.countryStatisticService = notNull(countryStatisticService);
        this.regionStatisticService = notNull(regionStatisticService);
        this.clusterStatisticService = notNull(clusterStatisticService);
    }

    public void countryStats(final String countryId) {
        notBlank(countryId);

        final ActivityStatistic statistics = countryStatisticService.statistics(Country.Id.create(countryId));

        statistics.getActivityQuantity();
    }
}
