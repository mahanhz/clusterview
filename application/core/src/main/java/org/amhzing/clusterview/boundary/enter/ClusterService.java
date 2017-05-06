package org.amhzing.clusterview.boundary.enter;

import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Country;
import org.amhzing.clusterview.domain.statistic.CourseStatistic;

import java.util.List;

public interface ClusterService {

    List<Cluster.Id> clusters(Country.Id country);

    void saveCourseStats(Cluster.Id clusterId, CourseStatistic courseStatistic);
}
