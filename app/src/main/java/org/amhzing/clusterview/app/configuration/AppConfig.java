package org.amhzing.clusterview.app.configuration;

import org.amhzing.clusterview.app.application.*;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Country;
import org.amhzing.clusterview.app.domain.model.Region;
import org.amhzing.clusterview.app.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.app.domain.model.statistic.CourseStatistic;
import org.amhzing.clusterview.app.domain.repository.*;
import org.amhzing.clusterview.app.user.UserServletFilter;
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
    private ClusterRepository clusterRepository;

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
    public ClusterService clusterService() {
        return new DefaultClusterService(clusterRepository);
    }

    @Bean
    public Filter userServletFilter() {
        return new UserServletFilter();
    }
}
