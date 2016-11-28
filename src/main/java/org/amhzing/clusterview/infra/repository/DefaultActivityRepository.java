package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Activity;
import org.amhzing.clusterview.domain.repository.ActivityRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ActivityEntity;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultActivityRepository implements ActivityRepository {

    private ActivityJpaRepository activityJpaRepository;

    public DefaultActivityRepository(final ActivityJpaRepository activityJpaRepository) {
        this.activityJpaRepository = notNull(activityJpaRepository);
    }

    @Override
    public List<Activity> activities() {
        final List<ActivityEntity> activityEntities = activityJpaRepository.findAll();

        return activityEntities.stream()
                               .map(activityEntity -> activity(activityEntity))
                               .collect(Collectors.toList());
    }

    private static Activity activity(final ActivityEntity activity) {
        return Activity.create(Activity.Id.create(activity.getId()), activity.getName());
    }
}
