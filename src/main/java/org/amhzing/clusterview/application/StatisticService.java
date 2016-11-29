package org.amhzing.clusterview.application;

import org.amhzing.clusterview.domain.model.ActivityStatistic;

public interface StatisticService<T> {

    ActivityStatistic statistics(T t);
}
