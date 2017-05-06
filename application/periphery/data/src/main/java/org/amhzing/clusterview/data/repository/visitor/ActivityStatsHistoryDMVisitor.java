package org.amhzing.clusterview.data.repository.visitor;

import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.DM_HOST;
import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryDMVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, DM_HOST);
    }
}
