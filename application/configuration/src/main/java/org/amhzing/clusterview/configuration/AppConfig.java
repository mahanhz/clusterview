package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.configuration.user.UserServletFilter;
import org.amhzing.clusterview.core.boundary.enter.*;
import org.amhzing.clusterview.core.boundary.exit.repository.*;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Country;
import org.amhzing.clusterview.core.domain.Region;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.amhzing.clusterview.core.usecase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AppConfig {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CoreActivityRepository coreActivityRepository;

    @Autowired
    private StatisticRepository<Country.Id, ActivityStatistic> countryStatisticRepository;

    @Autowired
    private StatisticRepository<Region.Id, ActivityStatistic> regionStatisticRepository;

    @Autowired
    private StatisticRepository<Cluster.Id, ActivityStatistic> clusterStatisticRepository;

    @Autowired
    private StatisticRepository<Country.Id, CourseStatistic> countryCourseStatisticRepository;

    @Autowired
    private StatisticRepository<Region.Id, CourseStatistic> regionCourseStatisticRepository;

    @Autowired
    private StatisticRepository<Cluster.Id, CourseStatistic> clusterCourseStatisticRepository;

    @Autowired
    private StatisticHistoryRepository statisticHistoryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ClusterRepository clusterRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public GroupService groupService() {
        return new DefaultGroupService(groupRepository);
    }

    @Bean
    public ActivityService activityService() {
        return new DefaultActivityService(activityRepository);
    }

    @Bean
    public CoreActivityService coreActivityService() {
        return new DefaultCoreActivityService(coreActivityRepository);
    }

    @Bean
    public StatisticService<ActivityStatistic> statisticService() {
        return new ActivityStatisticService(countryStatisticRepository,
                                            regionStatisticRepository,
                                            clusterStatisticRepository);
    }

    @Bean
    public StatisticService<CourseStatistic> courseStatisticService() {
        return new CourseStatisticService(countryCourseStatisticRepository,
                                          regionCourseStatisticRepository,
                                          clusterCourseStatisticRepository);
    }

    @Bean
    public StatisticHistoryService statisticHistoryService() {
        return new DefaultStatisticHistoryService(statisticHistoryRepository);
    }

    @Bean
    public RegionService regionService() {
        return new DefaultRegionService(regionRepository);
    }

    @Bean
    public ClusterService clusterService() {
        return new DefaultClusterService(clusterRepository);
    }

    @Bean
    public UserService userService() {
        return new DefaultUserService(userRepository);
    }

    @Bean
    public Filter userServletFilter() {
        return new UserServletFilter();
    }
}
