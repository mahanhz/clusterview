package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.appui.web.model.CoreActivityModel;
import org.amhzing.clusterview.core.boundary.enter.CoreActivityService;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class CoreActivityAdapter {

    private CoreActivityService coreActivityService;

    @Autowired
    public CoreActivityAdapter(final CoreActivityService coreActivityService) {
        this.coreActivityService = notNull(coreActivityService);
    }

    public List<CoreActivityModel> coreActivities() {
        final List<CoreActivity> coreActivities = coreActivityService.coreActivities();

        return coreActivities.stream()
                         .map(coreActivity -> coreActivityModel(coreActivity))
                         .sorted((a1, a2) -> a1.getName().compareTo(a2.getName()))
                         .collect(Collectors.toList());
    }

    private static CoreActivityModel coreActivityModel(final CoreActivity coreActivity) {
        return CoreActivityModel.create(coreActivity.getId().getId(), coreActivity.getName(), 0L, 0L, 0L);
    }
}
