package org.amhzing.clusterview.app.infra.repository;

import org.amhzing.clusterview.app.domain.model.Activity;
import org.amhzing.clusterview.app.infra.jpa.mapping.ActivityEntity;
import org.amhzing.clusterview.app.domain.repository.ActivityRepository;
import org.amhzing.clusterview.app.infra.jpa.repository.ActivityJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.app.cache.CacheSpec.ACTIVITIES_CACHE_NAME;
import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = ACTIVITIES_CACHE_NAME)
public class DefaultActivityRepository implements ActivityRepository {

    private ActivityJpaRepository activityJpaRepository;

    public DefaultActivityRepository(final ActivityJpaRepository activityJpaRepository) {
        this.activityJpaRepository = notNull(activityJpaRepository);
    }

    @Override
    @Cacheable(key= "#root.caches[0].name", unless = "#result == null or #result.isEmpty()")
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
