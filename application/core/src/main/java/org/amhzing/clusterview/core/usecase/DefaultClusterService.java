package org.amhzing.clusterview.core.usecase;

import org.amhzing.clusterview.core.boundary.enter.ClusterService;
import org.amhzing.clusterview.core.boundary.exit.repository.ClusterRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class DefaultClusterService implements ClusterService {

    private ClusterRepository clusterRepository;

    public DefaultClusterService(final ClusterRepository clusterRepository) {
        this.clusterRepository = notNull(clusterRepository);
    }

    @Override
    public List<Cluster.Id> clusters(final Country.Id id) {
        notNull(id);

        return clusterRepository.clusters(id);
    }

    @Override
    public void saveCourseStats(final Cluster.Id clusterId, final CourseStatistic courseStatistic) {
        notNull(clusterId);
        notNull(courseStatistic);

        clusterRepository.saveCourseStats(clusterId, courseStatistic);
    }
}
