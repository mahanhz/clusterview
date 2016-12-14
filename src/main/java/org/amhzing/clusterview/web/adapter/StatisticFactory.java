package org.amhzing.clusterview.web.adapter;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.CoreActivityModel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public final class StatisticFactory {

    public static final String PARTICIPANT = "participant";

    private StatisticFactory() {
        // To prevent instantiation
    }

    public static Map<String, Long> committedActivityQuantities(final ActivityStatistic statistics) {
        return statistics.getActivityQuantity()
                         .entrySet()
                         .stream()
                         .filter(entry -> !StringUtils.containsIgnoreCase(entry.getKey().getName(), PARTICIPANT))
                         .collect(toMap(entry -> entry.getKey().getName(),
                                        entry -> entry.getValue().getValue()));
    }

    public static List<CoreActivityModel> coreActivities(final ActivityStatistic statistics) {
        final Set<CoreActivity> coreActivities = statistics.getCoreActivities();

        return coreActivities.stream()
                             .map(coreActivity -> coreActivityModel(coreActivity))
                             .sorted((a1, a2) -> a1.getName().compareTo(a2.getName()))
                             .collect(Collectors.toList());
    }

    public static CoreActivityModel coreActivityModel(final CoreActivity coreActivity) {
        return CoreActivityModel.create(coreActivity.getId().getId(),
                                        coreActivity.getName(),
                                        coreActivity.getQuantity().getValue(),
                                        coreActivity.getTotalParticipants().getValue(),
                                        coreActivity.getCommunityOfInterest().getValue());
    }

    public static ActivityStatistic activityStatistic(final ActivityStatisticModel activityStatisticModel) {
        return ActivityStatistic.create(activityQuantity(activityStatisticModel),
                                        coreActivities(activityStatisticModel));
    }

    public static Map<Activity, Quantity> activityQuantity(final ActivityStatisticModel activityStatisticModel) {
        return activityStatisticModel.getActivityQuantity()
                                     .entrySet()
                                     .stream()
                                     .collect(Collectors.toMap(entry -> Activity.create(Activity.Id.create(entry.getKey()),
                                                                                        entry.getKey()),
                                                               entry -> Quantity.create(entry.getValue())));
    }

    public static Set<CoreActivity> coreActivities(final ActivityStatisticModel activityStatisticModel) {
        return activityStatisticModel.getCoreActivities().stream()
                                     .map(coreActivity -> coreActivity(coreActivity))
                                     .collect(Collectors.toSet());
    }

    public static CoreActivity coreActivity(final CoreActivityModel coreActivity) {
        return CoreActivity.create(CoreActivity.Id.create(coreActivity.getId()),
                                   coreActivity.getName(),
                                   Quantity.create(coreActivity.getQuantity()),
                                   Quantity.create(coreActivity.getTotalParticipants()),
                                   Quantity.create(coreActivity.getCommunityOfInterest()));
    }
}
