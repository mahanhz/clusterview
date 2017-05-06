package org.amhzing.clusterview.boundary.exit.repository;

public interface StatisticRepository<T, R> {

    R statistics(T t);
}
