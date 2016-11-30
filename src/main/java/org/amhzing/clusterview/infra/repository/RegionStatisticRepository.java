package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.repository.StatisticRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.MemberEntity;
import org.amhzing.clusterview.infra.jpa.mapping.RegionEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.RegionJpaRepository;

import java.util.Map;
import java.util.stream.Stream;

import static org.amhzing.clusterview.infra.repository.StatisticFactory.*;
import static org.amhzing.clusterview.infra.repository.StatisticFactory.activityQuantities;
import static org.apache.commons.lang3.Validate.notNull;

public class RegionStatisticRepository implements StatisticRepository<Region.Id> {

    private RegionJpaRepository regionJpaRepository;

    public RegionStatisticRepository(final RegionJpaRepository regionJpaRepository) {
        this.regionJpaRepository = notNull(regionJpaRepository);
    }

    @Override
    public ActivityStatistic statistics(final Region.Id id) {

        final RegionEntity region = regionJpaRepository.findOne(id.getId());

        final Stream<ClusterEntity> clusterEntityStream = clusterEntities(region);

        final Stream<TeamEntity> teamEntityStream = teamEntities(clusterEntityStream);

        final Stream<MemberEntity> memberEntityStream = memberEntities(teamEntityStream);

        final Stream<Activity> activityStream = activities(memberEntityStream);

        final Map<Activity, Quantity> activityQuantityMap = activityQuantities(activityStream);

        return ActivityStatistic.create(activityQuantityMap);
    }
}
