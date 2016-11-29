package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.ActivityStatistic;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Quantity;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.CountryEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.CountryJpaRepository;

import java.util.Map;
import java.util.stream.Stream;

import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

public class CountryStatisticRepository implements StatisticRepository<Country.Id> {

    private CountryJpaRepository countryJpaRepository;

    public CountryStatisticRepository(final CountryJpaRepository countryJpaRepository) {
        this.countryJpaRepository = notNull(countryJpaRepository);
    }

    @Override
    public ActivityStatistic statistics(final Country.Id id) {

        final CountryEntity country = countryJpaRepository.findOne(id.getId());

        final Stream<ClusterEntity> clusterEntityStream = clusterEntities(country);

        final Stream<TeamEntity> teamEntityStream = teamEntities(clusterEntityStream);

        final Stream<MemberEntity> memberEntityStream = memberEntities(teamEntityStream);

        final Stream<Activity> activityStream = activities(memberEntityStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        return ActivityStatistic.create(activityQuantityMap);
    }
}
