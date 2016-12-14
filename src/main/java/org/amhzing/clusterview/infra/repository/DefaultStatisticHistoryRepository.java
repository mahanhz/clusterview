package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.repository.StatisticHistoryRepository;
import org.amhzing.clusterview.infra.jpa.mapping.stats.ActivityStats;
import org.amhzing.clusterview.infra.jpa.mapping.stats.CoreActivityStats;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryPk;
import org.amhzing.clusterview.infra.jpa.repository.stats.StatsHistoryJpaRepository;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.*;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultStatisticHistoryRepository implements StatisticHistoryRepository {

    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    public DefaultStatisticHistoryRepository(final StatsHistoryJpaRepository statsHistoryJpaRepository) {
        this.statsHistoryJpaRepository = notNull(statsHistoryJpaRepository);
    }

    @Override
    public List<DatedActivityStatistic> history(final Cluster.Id clusterId) {
        final List<StatsHistoryEntity> statsHistory = statsHistoryJpaRepository.findByStatsHistoryPkClusterId(clusterId.getId());

        if (statsHistory == null) {
            return emptyList();
        }

        return statsHistory.stream()
                           .map(StatisticHistoryFactory::datedActivityStatistic)
                           .collect(Collectors.toList());
    }

    @Override
    public StatsHistoryEntity saveHistory(final Cluster.Id clusterId, final ActivityStatistic activityStatistic) {
        notNull(clusterId);
        notNull(activityStatistic);

        final StatsHistoryPk statsHistoryPk = StatsHistoryPk.create(clusterId.getId(), thisMonth());

        // TODO - Need to refactor this!!!
        final CoreActivityStats cc = new CoreActivityStats();
        final CoreActivityStats dm = new CoreActivityStats();
        final CoreActivityStats jyg = new CoreActivityStats();
        final CoreActivityStats sc = new CoreActivityStats();
        final Set<CoreActivity> coreActivities = activityStatistic.getCoreActivities();
        coreActivities.stream()
                      .forEach(coreActivity -> {
                          if (coreActivity.getId().getId().equalsIgnoreCase(CC)) {
                              cc.setQuantity((int) coreActivity.getQuantity().getValue());
                              cc.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
                              cc.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
                          } else if (coreActivity.getId().getId().equalsIgnoreCase(DM)) {
                              dm.setQuantity((int) coreActivity.getQuantity().getValue());
                              dm.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
                              dm.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
                          } else if (coreActivity.getId().getId().equalsIgnoreCase(JYG)) {
                              jyg.setQuantity((int) coreActivity.getQuantity().getValue());
                              jyg.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
                              jyg.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
                          } else if (coreActivity.getId().getId().equalsIgnoreCase(SC)) {
                              sc.setQuantity((int) coreActivity.getQuantity().getValue());
                              sc.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
                              sc.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
                          }
                      });

        final Map<Activity, Quantity> activityQuantity = activityStatistic.getActivityQuantity();

        final StatsHistoryEntity statsHistoryEntity = StatsHistoryEntity.create(statsHistoryPk, cc, dm, jyg, sc, activityStats(activityQuantity));

        return statsHistoryJpaRepository.save(statsHistoryEntity);
    }

    private ActivityStats activityStats(final Map<Activity, Quantity> activityQuantity) {
        // TODO - Need to refactor this!!!
        final ActivityStats activityStats = new ActivityStats();
        activityQuantity.entrySet().stream()
                        .forEach(entry -> {
                            if (entry.getKey().getName().equalsIgnoreCase(CC_TEACHER)) {
                                activityStats.setCcTeacher((int) entry.getValue().getValue());
                            } else if (entry.getKey().getName().equalsIgnoreCase(DM_HOST)) {
                                activityStats.setDmHost((int) entry.getValue().getValue());
                            } else if (entry.getKey().getName().equalsIgnoreCase(FIRESIDE_HOST)) {
                                activityStats.setFiresideHost((int) entry.getValue().getValue());
                            } else if (entry.getKey().getName().equalsIgnoreCase(JYG_ANIMATOR)) {
                                activityStats.setJygAnimator((int) entry.getValue().getValue());
                            } else if (entry.getKey().getName().equalsIgnoreCase(SC_TUTOR)) {
                                activityStats.setScTutor((int) entry.getValue().getValue());
                            }
                        });

        return  activityStats;
    }

    private Date thisMonth() {
        final SimpleDateFormat yyyyMMFormat = new SimpleDateFormat("yyyy-MM");
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        Date date = Date.from(utc.toInstant());
        try {
            date = yyyyMMFormat.parse(yyyyMMFormat.format(date));
        } catch (Exception ex) {
            // TODO - handle this
            return null;
        }

        return date;
    }
}
