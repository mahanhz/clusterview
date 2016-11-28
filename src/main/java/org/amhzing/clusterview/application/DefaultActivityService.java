package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.repository.ActivityRepository;

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
