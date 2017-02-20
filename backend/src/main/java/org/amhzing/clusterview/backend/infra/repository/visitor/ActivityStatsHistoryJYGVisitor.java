package org.amhzing.clusterview.backend.infra.repository.visitor;

import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.JYG_ANIMATOR;
import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryJYGVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, JYG_ANIMATOR);
    }
}
