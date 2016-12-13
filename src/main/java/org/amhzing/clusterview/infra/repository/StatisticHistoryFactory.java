package org.amhzing.clusterview.infra.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.infra.jpa.mapping.stats.ActivityStats;
import org.amhzing.clusterview.infra.jpa.mapping.stats.CoreActivityStats;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.infra.jpa.mapping.stats.StatsHistoryPk;

import static org.apache.commons.lang3.StringUtils.upperCase;

public final class StatisticHistoryFactory {

    private StatisticHistoryFactory() {
        // To prevent instantiation
    }

    public static DatedActivityStatistic datedActivityStatistic(final StatsHistoryEntity statsHistoryEntity) {

        final StatsHistoryPk statsHistoryPk = statsHistoryEntity.getStatsHistoryPk();
        final ActivityStats activityStats = statsHistoryEntity.getActivityStats();

        return DatedActivityStatistic.create(statsHistoryPk.getDate(),
                                             ActivityStatistic.create(activityStats(activityStats),
                                                                      coreActivityStats(statsHistoryEntity)));
    }

    private static ImmutableMap<Activity, Quantity> activityStats(final ActivityStats activityStats) {
        return ImmutableMap.of(activity("CC Teacher"), Quantity.create(activityStats.getCcTeacher()),
                               activity("DM Host"), Quantity.create(activityStats.getCcTeacher()),
                               activity("Fireside Host"), Quantity.create(activityStats.getCcTeacher()),
                               activity("JYG Animator"), Quantity.create(activityStats.getCcTeacher()),
                               activity("SC Tutor"), Quantity.create(activityStats.getCcTeacher()));
    }

    private static ImmutableSet<CoreActivity> coreActivityStats(final StatsHistoryEntity statsHistoryEntity) {
        return ImmutableSet.of(coreActivity(statsHistoryEntity.getCc(), "cc"),
                               coreActivity(statsHistoryEntity.getDm(), "dm"),
                               coreActivity(statsHistoryEntity.getJyg(), "jyg"),
                               coreActivity(statsHistoryEntity.getSc(), "sc"));
    }

    private static CoreActivity coreActivity(final CoreActivityStats coreActivityStats, final String type) {
        return CoreActivity.create(CoreActivity.Id.create(type),
                                   upperCase(type),
                                   Quantity.create(coreActivityStats.getQuantity()),
                                   Quantity.create(coreActivityStats.getTotalParticipants()),
                                   Quantity.create(coreActivityStats.getCoi()));
    }

    private static Activity activity(final String type) {
        return Activity.create(Activity.Id.create(type), type);
    }
}
