package org.amhzing.clusterview.core.boundary.enter;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;

import java.util.List;

public interface ClusterService {

    List<Cluster.Id> clusters(Country.Id country);

    void saveCourseStats(Cluster.Id clusterId, CourseStatistic courseStatistic);
}
