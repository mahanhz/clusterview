package org.amhzing.clusterview.acceptancetest;

import org.amhzing.clusterview.appui.MainUIApplication;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.TeamJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.stats.StatsHistoryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(classes = MainUIApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSteps {

    @LocalServerPort
    private int port = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private StatsHistoryJpaRepository statsHistoryJpaRepository;

    public int getPort() {
        return port;
    }

    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }

    public ClusterJpaRepository getClusterJpaRepository() {
        return clusterJpaRepository;
    }

    public TeamJpaRepository getTeamJpaRepository() {
        return teamJpaRepository;
    }

    public StatsHistoryJpaRepository getStatsHistoryJpaRepository() {
        return statsHistoryJpaRepository;
    }

    public long groupsSize(final TeamJpaRepository teamJpaRepository) {
        return teamJpaRepository.count();
    }
}
