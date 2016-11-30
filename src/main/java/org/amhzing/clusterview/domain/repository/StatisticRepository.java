package org.amhzing.clusterview.domain.repository;

import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;

public interface StatisticRepository<T> {

    ActivityStatistic statistics(T t);
}
