package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.core.boundary.exit.repository.*;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.data.jpa.repository.*;
import org.amhzing.clusterview.data.jpa.repository.stats.StatsHistoryJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.user.UserJpaRepository;
import org.amhzing.clusterview.data.repository.*;
import org.amhzing.clusterview.data.repository.user.DefaultUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.amhzing.clusterview.data.jpa.repository"})
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

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public StatisticRepository<Cluster.Id, CourseStatistic> clusterCourseStatisticRepository() {
        return new ClusterCourseStatisticRepository(clusterJpaRepository, courseJpaRepository);
    }

    @Bean
    public StatisticRepository<Region.Id, CourseStatistic> regionCourseStatisticRepository() {
        return new RegionCourseStatisticRepository(regionJpaRepository);
    }

    @Bean
    public StatisticRepository<Country.Id, CourseStatistic> countryCourseStatisticRepository() {
        return new CountryCourseStatisticRepository(countryJpaRepository);
    }

    @Bean
    public StatisticHistoryRepository statisticHistoryRepository() {
        return new DefaultStatisticHistoryRepository(statsHistoryJpaRepository);
    }

    @Bean
    public RegionRepository regionRepository() {
        return new DefaultRegionRepository(countryJpaRepository, regionJpaRepository);
    }

    @Bean
    public ClusterRepository clusterRepository() {
        return new DefaultClusterRepository(countryJpaRepository, clusterJpaRepository);
    }

    @Bean
    public UserRepository userRepository() {
        return new DefaultUserRepository(userJpaRepository, passwordEncoder);
    }
}
