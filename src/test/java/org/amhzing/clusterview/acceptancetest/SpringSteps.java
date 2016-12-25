package org.amhzing.clusterview.acceptancetest;

import org.amhzing.clusterview.ClusterviewApplication;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(classes = ClusterviewApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSteps {

    @LocalServerPort
    private int port = 0;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    public int getPort() {
        return port;
    }

    public ClusterJpaRepository getClusterJpaRepository() {
        return clusterJpaRepository;
    }

    public TeamJpaRepository getTeamJpaRepository() {
        return teamJpaRepository;
    }

    public long groupsSize(final TeamJpaRepository teamJpaRepository) {
        return teamJpaRepository.count();
    }
}
