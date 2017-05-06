package org.amhzing.clusterview.repository.visitor;

import org.amhzing.clusterview.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.statistic.CoreActivity;
import org.amhzing.clusterview.domain.statistic.CoreActivityStatsHistoryVisitor;

import java.util.Optional;

import static org.amhzing.clusterview.repository.StatisticHistoryFactory.JYG;
import static org.amhzing.clusterview.repository.StatisticHistoryFactory.coreActivity;

public class CoreActivityStatsHistoryJYGVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return coreActivity(activityStatistic, JYG);
    }
}
