package org.amhzing.clusterview.usecase;

import org.amhzing.clusterview.boundary.enter.ActivityService;
import org.amhzing.clusterview.boundary.exit.repository.ActivityRepository;
import org.amhzing.clusterview.domain.Activity;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultActivityService implements ActivityService {

    private ActivityRepository activityRepository;

    public DefaultActivityService(final ActivityRepository activityRepository) {
        this.activityRepository = notNull(activityRepository);
    }

    @Override
    public List<Activity> activities() {
        return activityRepository.activities();
    }
}
