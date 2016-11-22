package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.amhzing.clusterview.infra.repository.DefaultClusterRepository;
import org.amhzing.clusterview.infra.repository.DefaultGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Bean
    public ClusterRepository clusterRepository() {
        return new DefaultClusterRepository(clusterJpaRepository);
    }

    @Bean
    public GroupRepository groupRepository() {
        return new DefaultGroupRepository(teamJpaRepository);
    }
}
