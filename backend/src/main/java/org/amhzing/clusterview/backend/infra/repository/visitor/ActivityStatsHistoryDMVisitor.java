package org.amhzing.clusterview.backend.infra.repository.visitor;

import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.DM_HOST;
import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryDMVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, DM_HOST);
    }
}
