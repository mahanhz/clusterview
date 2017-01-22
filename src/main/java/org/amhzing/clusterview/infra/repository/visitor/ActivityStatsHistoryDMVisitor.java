package org.amhzing.clusterview.infra.repository.visitor;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.DM_HOST;
import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryDMVisitor implements ActivityStatsHistoryVisitor {


    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, DM_HOST);
    }
}
