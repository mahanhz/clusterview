package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.adapter.web.api.ReferenceActivityDTO;
import org.amhzing.clusterview.core.boundary.enter.ActivityService;
import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.Validate.notNull;

public class ActivityAdapter {

    private ActivityService activityService;
    private CoreActivityService coreActivityService;

    public ActivityAdapter(final ActivityService activityService, final CoreActivityService coreActivityService) {
        this.activityService = notNull(activityService);
        this.coreActivityService = notNull(coreActivityService);
    }

    public ReferenceActivitiesDTO activities() {
        final List<Activity> activities = activityService.activities();

        final List<ReferenceActivityDTO> referencedActivities = activities.stream()
                                                                          .map(activity -> activityDto(activity))
                                                                          .sorted(comparing(a -> a.name))
                                                                          .collect(Collectors.toList());

        return new ReferenceActivitiesDTO(referencedActivities);
    }

    public ReferenceActivitiesDTO coreActivities() {
        final List<CoreActivity> activities = coreActivityService.coreActivities();

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
