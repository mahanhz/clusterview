package org.amhzing.clusterview.core.domain.statistic;

import java.util.Optional;

public interface StatsHistoryElement {

    Optional<CoreActivity> accept(CoreActivityStatsHistoryVisitor visitor);

    int accept(ActivityStatsHistoryVisitor visitor);
}
