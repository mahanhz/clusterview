package org.amhzing.clusterview.backend.configuration;

import org.amhzing.clusterview.backend.domain.repository.*;
import org.amhzing.clusterview.backend.infra.jpa.repository.*;
import org.amhzing.clusterview.backend.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.backend.infra.repository.*;
import org.amhzing.clusterview.backend.domain.model.Cluster;
import org.amhzing.clusterview.backend.domain.model.Country;
import org.amhzing.clusterview.backend.domain.model.Region;
import org.amhzing.clusterview.backend.domain.model.statistic.ActivityStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.amhzing.clusterview.backend.infra.jpa.repository"})
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

    @Autowired
    private StatsHistoryJpaRepository statsHistoryJpaRepository;

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
    public StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository() {
        return new CountryStatisticRepository(countryJpaRepository, groupRepository());
    }

    @Bean
    public StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository() {
        return new RegionStatisticRepository(regionJpaRepository, groupRepository());
    }

    @Bean
    public StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository() {
        return new ClusterStatisticRepository(clusterJpaRepository, groupRepository());
    }

    @Bean
    public StatisticHistoryRepository statisticHistoryRepository() {
        return new DefaultStatisticHistoryRepository(statsHistoryJpaRepository);
    }

    @Bean
    public ClusterRepository clusterRepository() {
        return new DefaultClusterRepository(countryJpaRepository);
    }
}
