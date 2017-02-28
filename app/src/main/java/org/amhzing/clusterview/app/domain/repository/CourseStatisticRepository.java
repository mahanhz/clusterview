package org.amhzing.clusterview.app.domain.repository;

import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;

public interface CourseStatisticRepository<T> {

    CourseStatistic statistics(T t);
}
