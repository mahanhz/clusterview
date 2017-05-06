package org.amhzing.clusterview.core.boundary.exit.repository;

public interface StatisticRepository<T, R> {

    R statistics(T t);
}
