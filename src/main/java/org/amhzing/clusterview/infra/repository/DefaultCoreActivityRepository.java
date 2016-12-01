package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.domain.model.statistic.Quantity;
import org.amhzing.clusterview.domain.repository.CoreActivityRepository;
import org.amhzing.clusterview.infra.jpa.mapping.CoreActivityEntity;
import org.amhzing.clusterview.infra.jpa.repository.CoreActivityJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultCoreActivityRepository implements CoreActivityRepository {

    private CoreActivityJpaRepository coreActivityJpaRepository;

    public DefaultCoreActivityRepository(final CoreActivityJpaRepository coreActivityJpaRepository) {
        this.coreActivityJpaRepository = notNull(coreActivityJpaRepository);
    }

    @Override
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
                                   Quantity.create(0L));
    }
}
