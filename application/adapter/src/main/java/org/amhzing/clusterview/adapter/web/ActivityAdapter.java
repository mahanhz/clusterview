package org.amhzing.clusterview.adapter.web;

import org.amhzing.clusterview.adapter.web.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.core.boundary.enter.ActivityService;
import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.core.domain.Activity;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;

import java.util.List;

import static org.amhzing.clusterview.adapter.web.util.ActivityDtoFactory.activitiesDTO;
import static org.amhzing.clusterview.adapter.web.util.ActivityDtoFactory.coreActivitiesDTO;
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

        return activitiesDTO(activities);
    }

    public ReferenceActivitiesDTO coreActivities() {
        final List<CoreActivity> activities = coreActivityService.coreActivities();

        return coreActivitiesDTO(activities);
    }
}
