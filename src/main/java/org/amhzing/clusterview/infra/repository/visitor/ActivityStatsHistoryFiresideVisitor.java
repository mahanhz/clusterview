package org.amhzing.clusterview.infra.repository.visitor;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.FIRESIDE_HOST;
import static org.amhzing.clusterview.infra.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryFiresideVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, FIRESIDE_HOST);
    }
}
