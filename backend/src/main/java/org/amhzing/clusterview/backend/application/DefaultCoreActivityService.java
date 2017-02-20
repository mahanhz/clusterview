package org.amhzing.clusterview.backend.application;

import org.amhzing.clusterview.backend.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.backend.domain.repository.CoreActivityRepository;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultCoreActivityService implements CoreActivityService {

    private CoreActivityRepository coreActivityRepository;

    public DefaultCoreActivityService(final CoreActivityRepository coreActivityRepository) {
        this.coreActivityRepository = notNull(coreActivityRepository);
    }

    @Override
    public List<CoreActivity> coreActivities() {
        return coreActivityRepository.coreActivities();
    }
}
