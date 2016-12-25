package org.amhzing.clusterview.acceptancetest;

import org.amhzing.clusterview.ClusterviewApplication;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootTest(classes = ClusterviewApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSteps {

    @LocalServerPort
    private int port = 0;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    public int getPort() {
        return port;
    }

    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }

    public TeamJpaRepository getTeamJpaRepository() {
        return teamJpaRepository;
    }

    public long groupsSize(final TeamJpaRepository teamJpaRepository) {
        return teamJpaRepository.count();
    }
}
