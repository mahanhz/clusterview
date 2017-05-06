package org.amhzing.clusterview.repository.visitor;

import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.CoreActivity;
import org.amhzing.clusterview.domain.statistic.CoreActivityStatsHistoryVisitor;
import org.amhzing.clusterview.repository.StatisticHistoryFactory;

import java.util.Optional;

public class CoreActivityStatsHistorySCVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.coreActivity(activityStatistic, StatisticHistoryFactory.SC);
    }
}
