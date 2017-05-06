package org.amhzing.clusterview.data.repository.visitor;

import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CoreActivity;
import org.amhzing.clusterview.core.domain.statistic.CoreActivityStatsHistoryVisitor;

import java.util.Optional;

import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.DM;
import static org.amhzing.clusterview.data.repository.StatisticHistoryFactory.coreActivity;

public class CoreActivityStatsHistoryDMVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return coreActivity(activityStatistic, DM);
    }
}
