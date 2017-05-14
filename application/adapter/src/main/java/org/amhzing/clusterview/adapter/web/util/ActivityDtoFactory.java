package org.amhzing.clusterview.adapter.web.util;

import org.amhzing.clusterview.adapter.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.ReferenceActivityDTO;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.Validate.notNull;

public final class ActivityDtoFactory {

    private ActivityDtoFactory() {
        // To prevent instantiation
    }

    public static ReferenceActivitiesDTO activitiesDTO(final List<Activity> activities) {
        notNull(activities);

        final List<ReferenceActivityDTO> referencedActivities = activities.stream()
                                                                          .map(activity -> activityDto(activity))
                                                                          .sorted(comparing(a -> a.name))
                                                                          .collect(Collectors.toList());

        return new ReferenceActivitiesDTO(referencedActivities);
    }

    public static ReferenceActivitiesDTO coreActivitiesDTO(final List<CoreActivity> activities) {
        notNull(activities);

        final List<ReferenceActivityDTO> referencedActivities = activities.stream()
                                                                          .map(coreActivity -> coreActivityDto(coreActivity))
                                                                          .sorted(comparing(a -> a.name))
                                                                          .collect(Collectors.toList());

        return new ReferenceActivitiesDTO(referencedActivities);
    }

    private static ReferenceActivityDTO activityDto(final Activity activity) {
        return new ReferenceActivityDTO(activity.getId().getId(), activity.getName());
    }

    private static ReferenceActivityDTO coreActivityDto(final CoreActivity coreActivity) {
        return new ReferenceActivityDTO(coreActivity.getId().getId(), coreActivity.getName());
    }
}
