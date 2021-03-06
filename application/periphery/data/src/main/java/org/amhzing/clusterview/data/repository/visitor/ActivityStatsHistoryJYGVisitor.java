package org.amhzing.clusterview.data.repository.visitor;

import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.JYG_ANIMATOR;
import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryJYGVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, JYG_ANIMATOR);
    }
}
