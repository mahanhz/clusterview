package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.application.ClusterService;
import org.amhzing.clusterview.application.DefaultClusterService;
import org.amhzing.clusterview.domain.repository.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private ClusterRepository clusterRepository;

    @Bean
    public ClusterService clusterService() {
        return new DefaultClusterService(clusterRepository);
    }
}
