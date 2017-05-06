package org.amhzing.clusterview.core.boundary.exit.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;

import java.util.List;

public interface ClusterRepository {

    List<Cluster.Id> clusters(Country.Id id);

    void saveCourseStats(Cluster.Id id, CourseStatistic courseStatistic);
}
