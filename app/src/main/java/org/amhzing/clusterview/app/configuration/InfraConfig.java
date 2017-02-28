package org.amhzing.clusterview.app.configuration;

import org.amhzing.clusterview.app.domain.repository.*;
import org.amhzing.clusterview.app.infra.jpa.repository.*;
import org.amhzing.clusterview.app.infra.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.app.infra.repository.*;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.amhzing.clusterview.app.infra.jpa.repository"})
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
    public CourseStatisticRepository<Cluster.Id> clusterCourseStatisticRepository() {
        return new ClusterCourseStatisticRepository(clusterJpaRepository);
    }

    @Bean
    public CourseStatisticRepository<Region.Id> regionCourseStatisticRepository() {
        return new RegionCourseStatisticRepository(regionJpaRepository);
    }

    @Bean
    public CourseStatisticRepository<Country.Id> countryCourseStatisticRepository() {
        return new CountryCourseStatisticRepository(countryJpaRepository);
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
