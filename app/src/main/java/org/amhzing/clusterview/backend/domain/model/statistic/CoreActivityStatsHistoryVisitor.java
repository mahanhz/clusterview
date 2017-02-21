package org.amhzing.clusterview.backend.domain.model.statistic;

import java.util.Optional;

public interface CoreActivityStatsHistoryVisitor {

    Optional<CoreActivity> visit(ActivityStatistic activityStatistic);
}
