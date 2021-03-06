package org.amhzing.clusterview.data.repository.visitor;

import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatsHistoryVisitor;
import org.amhzing.clusterview.data.repository.StatisticHistoryFactory;

public class ActivityStatsHistoryCCVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.activityStats(activityStatistic, StatisticHistoryFactory.CC_TEACHER);
    }
}
