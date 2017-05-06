package org.amhzing.clusterview.boundary.exit.repository;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;

import java.util.List;

public interface ClusterRepository {

    List<Cluster.Id> clusters(Country.Id id);

    void saveCourseStats(Cluster.Id id, CourseStatistic courseStatistic);
}
