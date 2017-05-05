package org.amhzing.clusterview.domain.statistic;

import java.util.Optional;

public interface CoreActivityStatsHistoryVisitor {

    Optional<CoreActivity> visit(ActivityStatistic activityStatistic);
}
