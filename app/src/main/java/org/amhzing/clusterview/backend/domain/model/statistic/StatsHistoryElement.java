package org.amhzing.clusterview.backend.domain.model.statistic;

import java.util.Optional;

public interface StatsHistoryElement {

    Optional<CoreActivity> accept(CoreActivityStatsHistoryVisitor visitor);

    int accept(ActivityStatsHistoryVisitor visitor);
}
