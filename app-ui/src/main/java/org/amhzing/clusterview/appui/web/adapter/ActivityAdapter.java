package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.appui.web.model.ActivityModel;
import org.amhzing.clusterview.core.boundary.enter.ActivityService;
import org.amhzing.clusterview.core.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ActivityAdapter {

    private ActivityService activityService;

    @Autowired
    public ActivityAdapter(final ActivityService activityService) {
        this.activityService = notNull(activityService);
    }

    public List<ActivityModel> activities() {
        final List<Activity> activities = activityService.activities();

        return activities.stream()
                         .map(activity -> activityModel(activity))
                         .sorted((a1, a2) -> a1.getName().compareTo(a2.getName()))
                         .collect(Collectors.toList());
    }

    private static ActivityModel activityModel(final Activity activity) {
        return ActivityModel.create(activity.getId().getId(), activity.getName());
    }
}
