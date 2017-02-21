package org.amhzing.clusterview.app.infra.repository.visitor;

import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.infra.repository.StatisticHistoryFactory;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivityStatsHistoryVisitor;

import java.util.Optional;

public class CoreActivityStatsHistoryCCVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return StatisticHistoryFactory.coreActivity(activityStatistic, StatisticHistoryFactory.CC);
    }
}
