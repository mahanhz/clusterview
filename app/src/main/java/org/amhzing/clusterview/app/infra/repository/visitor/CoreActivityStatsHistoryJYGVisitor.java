package org.amhzing.clusterview.app.infra.repository.visitor;

import org.amhzing.clusterview.app.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CoreActivityStatsHistoryVisitor;

import java.util.Optional;

import static org.amhzing.clusterview.app.infra.repository.StatisticHistoryFactory.JYG;
import static org.amhzing.clusterview.app.infra.repository.StatisticHistoryFactory.coreActivity;

public class CoreActivityStatsHistoryJYGVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return coreActivity(activityStatistic, JYG);
    }
}
