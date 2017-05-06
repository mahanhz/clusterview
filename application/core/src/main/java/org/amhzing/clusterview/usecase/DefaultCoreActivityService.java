package org.amhzing.clusterview.usecase;

import org.amhzing.clusterview.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.boundary.exit.repository.CoreActivityRepository;
import org.amhzing.clusterview.domain.statistic.CoreActivity;

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
