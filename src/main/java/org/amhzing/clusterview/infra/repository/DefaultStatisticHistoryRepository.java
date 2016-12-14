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
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        notNull(clusterId);

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

        final CoreActivityStats cc = new CoreActivityStats();
        final CoreActivityStats dm = new CoreActivityStats();
        final CoreActivityStats jyg = new CoreActivityStats();
        final CoreActivityStats sc = new CoreActivityStats();

        populateCoreActivitiesStats(activityStatistic, cc, dm, jyg, sc);

        final Map<Activity, Quantity> activityQuantity = activityStatistic.getActivityQuantity();

        final StatsHistoryEntity statsHistoryEntity = StatsHistoryEntity.create(statsHistoryPk,
                                                                                cc, dm, jyg, sc,
                                                                                activityStats(activityQuantity));

        return statsHistoryJpaRepository.save(statsHistoryEntity);
    }

    private void populateCoreActivitiesStats(final ActivityStatistic activityStatistic,
                                             final CoreActivityStats cc,
                                             final CoreActivityStats dm,
                                             final CoreActivityStats jyg,
                                             final CoreActivityStats sc) {
        activityStatistic.getCoreActivities()
                         .stream()
                         .forEach(coreActivity -> {
                             if (coreActivity.getId().getId().equalsIgnoreCase(CC)) {
                                 coreActivityStats(cc, coreActivity);
                             } else if (coreActivity.getId().getId().equalsIgnoreCase(DM)) {
                                 coreActivityStats(dm, coreActivity);
                             } else if (coreActivity.getId().getId().equalsIgnoreCase(JYG)) {
                                 coreActivityStats(jyg, coreActivity);
                             } else if (coreActivity.getId().getId().equalsIgnoreCase(SC)) {
                                 coreActivityStats(sc, coreActivity);
                             }
                         });
    }

    private void coreActivityStats(final CoreActivityStats coreActivityStats, final CoreActivity coreActivity) {
        coreActivityStats.setQuantity((int) coreActivity.getQuantity().getValue());
        coreActivityStats.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
        coreActivityStats.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
    }

    private ActivityStats activityStats(final Map<Activity, Quantity> activityQuantity) {

        final ActivityStats activityStats = new ActivityStats();
        activityQuantity.entrySet().stream()
                        .forEach(entry -> {
                            if (activityName(entry).equalsIgnoreCase(CC_TEACHER)) {
                                activityStats.setCcTeacher(activityValue(entry));
                            } else if (activityName(entry).equalsIgnoreCase(DM_HOST)) {
                                activityStats.setDmHost(activityValue(entry));
                            } else if (activityName(entry).equalsIgnoreCase(FIRESIDE_HOST)) {
                                activityStats.setFiresideHost(activityValue(entry));
                            } else if (activityName(entry).equalsIgnoreCase(JYG_ANIMATOR)) {
                                activityStats.setJygAnimator(activityValue(entry));
                            } else if (activityName(entry).equalsIgnoreCase(SC_TUTOR)) {
                                activityStats.setScTutor(activityValue(entry));
                            }
                        });

        return  activityStats;
    }

    private String activityName(final Map.Entry<Activity, Quantity> entry) {
        return entry.getKey().getName();
    }

    private int activityValue(final Map.Entry<Activity, Quantity> entry) {
        return (int) entry.getValue().getValue();
    }

    private Date thisMonth() {
        final SimpleDateFormat yyyyMMFormat = new SimpleDateFormat("yyyy-MM");
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        Date date = Date.from(utc.toInstant());

        try {
            date = yyyyMMFormat.parse(yyyyMMFormat.format(date));
        } catch (Exception ex) {
            throw new DateTimeException("Could not construct this months date from " + date , ex);
        }

        return date;
    }
}
