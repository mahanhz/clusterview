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

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

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

    public static Date thisMonth() {
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

    public static void populateCoreActivitiesStats(final ActivityStatistic activityStatistic,
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

    public static ActivityStats activityStats(final Map<Activity, Quantity> activityQuantity) {

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

    private static void coreActivityStats(final CoreActivityStats coreActivityStats, final CoreActivity coreActivity) {
        coreActivityStats.setQuantity((int) coreActivity.getQuantity().getValue());
        coreActivityStats.setTotalParticipants((int) coreActivity.getTotalParticipants().getValue());
        coreActivityStats.setCoi((int) coreActivity.getCommunityOfInterest().getValue());
    }

    private static String activityName(final Map.Entry<Activity, Quantity> entry) {
        return entry.getKey().getName();
    }

    private static int activityValue(final Map.Entry<Activity, Quantity> entry) {
        return (int) entry.getValue().getValue();
    }
}
