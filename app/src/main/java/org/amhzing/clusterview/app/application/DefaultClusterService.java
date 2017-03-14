package org.amhzing.clusterview.app.application;

import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.ClusterRepository;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    public void saveCourseStats(final Cluster.Id clusterId, final CourseStatistic courseStatistic) {
        notNull(clusterId);
        notNull(courseStatistic);

        clusterRepository.saveCourseStats(clusterId, courseStatistic);
    }
}
