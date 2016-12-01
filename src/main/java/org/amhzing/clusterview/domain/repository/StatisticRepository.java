package org.amhzing.clusterview.domain.repository;

public interface StatisticRepository<T, R> {

    R statistics(T t);
}
