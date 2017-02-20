package org.amhzing.clusterview.backend.infra.repository.visitor;

import org.amhzing.clusterview.backend.domain.model.statistic.CoreActivity;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.backend.domain.model.statistic.CoreActivityStatsHistoryVisitor;

import java.util.Optional;

import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.DM;
import static org.amhzing.clusterview.backend.infra.repository.StatisticHistoryFactory.coreActivity;

public class CoreActivityStatsHistoryDMVisitor implements CoreActivityStatsHistoryVisitor {

    @Override
    public Optional<CoreActivity> visit(final ActivityStatistic activityStatistic) {
        return coreActivity(activityStatistic, DM);
    }
}
