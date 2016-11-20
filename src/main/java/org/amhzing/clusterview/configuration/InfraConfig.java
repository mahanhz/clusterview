package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.repository.DefaultClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Bean
    public ClusterRepository clusterRepository() {
        return new DefaultClusterRepository(clusterJpaRepository);
    }
}
