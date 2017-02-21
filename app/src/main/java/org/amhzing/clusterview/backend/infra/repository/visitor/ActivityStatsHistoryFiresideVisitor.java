package org.amhzing.clusterview.backend.infra.repository.visitor;

import org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatsHistoryVisitor;

public class ActivityStatsHistoryFiresideVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.activityStats(activityStatistic, StatisticHistoryFactory.FIRESIDE_HOST);
    }
}
