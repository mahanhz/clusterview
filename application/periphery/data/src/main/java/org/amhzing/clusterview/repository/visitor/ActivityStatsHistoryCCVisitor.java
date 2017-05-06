package org.amhzing.clusterview.repository.visitor;

import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.ActivityStatsHistoryVisitor;
import org.amhzing.clusterview.repository.StatisticHistoryFactory;

public class ActivityStatsHistoryCCVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.activityStats(activityStatistic, StatisticHistoryFactory.CC_TEACHER);
    }
}
