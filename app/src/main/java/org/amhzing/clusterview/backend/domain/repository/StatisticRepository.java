package org.amhzing.clusterview.backend.domain.repository;

public interface StatisticRepository<T, R> {

    R statistics(T t);
}
