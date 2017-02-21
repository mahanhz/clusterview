package org.amhzing.clusterview.app.domain.model.statistic;

import java.util.Optional;

public interface StatsHistoryElement {

    Optional<CoreActivity> accept(CoreActivityStatsHistoryVisitor visitor);

    int accept(ActivityStatsHistoryVisitor visitor);
}
