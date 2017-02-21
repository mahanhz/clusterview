package org.amhzing.clusterview.app.infra.repository.visitor;

import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatsHistoryVisitor;

import static org.amhzing.clusterview.app.infra.repository.StatisticHistoryFactory.JYG_ANIMATOR;
import static org.amhzing.clusterview.app.infra.repository.StatisticHistoryFactory.activityStats;

public class ActivityStatsHistoryJYGVisitor implements ActivityStatsHistoryVisitor {

    @Override
    public int visit(final ActivityStatistic activityStatistic) {
        return activityStats(activityStatistic, JYG_ANIMATOR);
    }
}
