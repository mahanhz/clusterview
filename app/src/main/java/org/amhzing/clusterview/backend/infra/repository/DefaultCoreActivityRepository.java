package org.amhzing.clusterview.backend.infra.repository;

import org.amhzing.clusterview.backend.cache.CacheSpec;
import org.amhzing.clusterview.backend.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.backend.domain.model.statistic.Quantity;
import org.amhzing.clusterview.backend.domain.repository.CoreActivityRepository;
import org.amhzing.clusterview.backend.infra.jpa.mapping.CoreActivityEntity;
import org.amhzing.clusterview.backend.infra.jpa.repository.CoreActivityJpaRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@CacheConfig(cacheNames = CacheSpec.CORE_ACTIVITIES_CACHE_NAME)
public class DefaultCoreActivityRepository implements CoreActivityRepository {

    private CoreActivityJpaRepository coreActivityJpaRepository;

    public DefaultCoreActivityRepository(final CoreActivityJpaRepository coreActivityJpaRepository) {
        this.coreActivityJpaRepository = notNull(coreActivityJpaRepository);
    }

    @Override
    @Cacheable(key= "#root.caches[0].name", unless = "#result == null or #result.isEmpty()")
    public List<CoreActivity> coreActivities() {
        final List<CoreActivityEntity> coreActivityEntities = coreActivityJpaRepository.findAll();

        return coreActivityEntities.stream()
                .map(coreActivityEntity -> coreActivity(coreActivityEntity))
                .collect(Collectors.toList());
    }

    private static CoreActivity coreActivity(final CoreActivityEntity coreActivity) {
        return CoreActivity.create(CoreActivity.Id.create(coreActivity.getId()),
                                   coreActivity.getName(),
                                   Quantity.create(0L),
                                   Quantity.create(0L),
                                   Quantity.create(0L));
    }
}
