package org.amhzing.clusterview.app.domain.repository;

public interface StatisticRepository<T, R> {

    R statistics(T t);
}
