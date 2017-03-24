package org.amhzing.clusterview.app.infra.repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.app.domain.model.Activity;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.model.statistic.DatedActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.Quantity;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.ActivityStats;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.CoreActivityStats;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryEntity;
import org.amhzing.clusterview.app.infra.jpa.mapping.stats.StatsHistoryPk;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import static org.apache.commons.lang3.StringUtils.upperCase;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

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
        final ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        Date date = Date.from(utc.toInstant());

        try {
            final SimpleDateFormat yyyyMMFormat = new SimpleDateFormat("yyyy-MM");
            yyyyMMFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));

            date = yyyyMMFormat.parse(yyyyMMFormat.format(date));
        } catch (Exception ex) {
            throw new DateTimeException("Could not construct this months date from " + date , ex);
        }

        return date;
    }

    public static int activityStats(final ActivityStatistic activityStatistic,
                                    final String type) {
        notNull(activityStatistic);
        notBlank(type);

        return activityStatistic.getActivityQuantity()
                         .entrySet()
                         .stream()
                         .filter(entry -> activityName(entry).equalsIgnoreCase(type))
                         .mapToInt(entry -> activityValue(entry))
                         .reduce((a, b) -> {
                             throw new IllegalStateException("Duplicate stats found for type " + type);
                         })
                         .orElse(0);
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

    public static Optional<CoreActivity> coreActivity(final ActivityStatistic activityStatistic, final String type) {
        notNull(activityStatistic);
        notBlank(type);

        return activityStatistic.getCoreActivities()
                                .stream()
                                .filter(coreActivity -> coreActivity.getId().getId().equalsIgnoreCase(type))
                                .reduce((a, b) -> {
                                    throw new IllegalStateException("Duplicate stats found for type " + type);
                                });
    }

    public static CoreActivityStats coreActivityStats(final Optional<CoreActivity> coreActivity) {
        if (!coreActivity.isPresent()) {
            return new CoreActivityStats();
        }

        final CoreActivity presentCoreActivity = coreActivity.get();
        final int quantity = (int) presentCoreActivity.getQuantity().getValue();
        final int totalParticipants = (int) presentCoreActivity.getTotalParticipants().getValue();
        final int coi = (int) presentCoreActivity.getCommunityOfInterest().getValue();

        return CoreActivityStats.create(quantity, totalParticipants, coi);
    }

    public static String activityName(final Map.Entry<Activity, Quantity> entry) {
        return entry.getKey().getName();
    }

    private static int activityValue(final Map.Entry<Activity, Quantity> entry) {
        return (int) entry.getValue().getValue();
    }
}
