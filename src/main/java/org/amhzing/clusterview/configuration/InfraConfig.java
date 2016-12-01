package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.domain.repository.ActivityRepository;
import org.amhzing.clusterview.domain.repository.CoreActivityRepository;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.repository.*;
import org.amhzing.clusterview.infra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfraConfig {

    @Autowired
    private CountryJpaRepository countryJpaRepository;

    @Autowired
    private RegionJpaRepository regionJpaRepository;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private ActivityJpaRepository activityJpaRepository;

    @Autowired
    private CoreActivityJpaRepository coreActivityJpaRepository;

    @Bean
    public GroupRepository groupRepository() {
        return new DefaultGroupRepository(clusterJpaRepository, teamJpaRepository, activityJpaRepository);
    }

    @Bean
    public ActivityRepository activityRepository() {
        return new DefaultActivityRepository(activityJpaRepository);
    }

    @Bean
    public CoreActivityRepository coreActivityRepository() {
        return new DefaultCoreActivityRepository(coreActivityJpaRepository);
    }

    @Bean
    public CountryStatisticRepository countryStatisticRepository() {
        return new CountryStatisticRepository(countryJpaRepository);
    }

    @Bean
    public RegionStatisticRepository regionStatisticRepository() {
        return new RegionStatisticRepository(regionJpaRepository);
    }

    @Bean
    public ClusterStatisticRepository clusterStatisticRepository() {
        return new ClusterStatisticRepository(clusterJpaRepository);
    }
}
