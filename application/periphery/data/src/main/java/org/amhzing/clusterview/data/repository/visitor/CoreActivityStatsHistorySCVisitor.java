package org.amhzing.clusterview.data.repository.visitor;

import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.CoreActivityStatsHistoryVisitor;
import org.amhzing.clusterview.data.repository.StatisticHistoryFactory;

import java.util.Optional;

public class CoreActivityStatsHistorySCVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.coreActivity(activityStatistic, StatisticHistoryFactory.SC);
    }
}
