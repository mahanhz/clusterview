package org.amhzing.clusterview.repository.visitor;

import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.repository.StatisticHistoryFactory.JYG_ANIMATOR;
import static org.amhzing.clusterview.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryJYGVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, JYG_ANIMATOR);
    }
}
