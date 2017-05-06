package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.core.boundary.exit.repository.CoreActivityRepository;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;

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
