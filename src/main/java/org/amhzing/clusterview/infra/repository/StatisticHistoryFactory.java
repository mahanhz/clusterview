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

    public static final String CC = "cc";
    public static final String DM = "dm";
    public static final String JYG = "jyg";
    public static final String SC = "sc";
    public static final String CC_TEACHER = "CC Teacher";
    public static final String DM_HOST = "DM Host";
    public static final String FIRESIDE_HOST = "Fireside Host";
    public static final String JYG_ANIMATOR = "JYG Animator";
    public static final String SC_TUTOR = "SC Tutor";

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
        return ImmutableMap.of(activity(CC_TEACHER), Quantity.create(activityStats.getCcTeacher()),
                               activity(DM_HOST), Quantity.create(activityStats.getDmHost()),
                               activity(FIRESIDE_HOST), Quantity.create(activityStats.getFiresideHost()),
                               activity(JYG_ANIMATOR), Quantity.create(activityStats.getJygAnimator()),
                               activity(SC_TUTOR), Quantity.create(activityStats.getScTutor()));
    }

    private static ImmutableSet<CoreActivity> coreActivityStats(final StatsHistoryEntity statsHistoryEntity) {
        return ImmutableSet.of(coreActivity(statsHistoryEntity.getCc(), CC),
                               coreActivity(statsHistoryEntity.getDm(), DM),
                               coreActivity(statsHistoryEntity.getJyg(), JYG),
                               coreActivity(statsHistoryEntity.getSc(), SC));
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
