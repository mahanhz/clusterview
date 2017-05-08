package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.adapter.web.*;
import org.amhzing.clusterview.core.boundary.enter.*;
import org.amhzing.clusterview.core.domain.statistic.ActivityStatistic;
import org.amhzing.clusterview.core.domain.statistic.CourseStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CoreActivityService coreActivityService;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private StatisticHistoryService statisticHistoryService;

    @Autowired
    private StatisticService<ActivityStatistic> statisticService;

    @Autowired
    private StatisticService<CourseStatistic> courseStatisticService;

    @Autowired
    private UserService userService;

    @Bean
    public ActivityAdapter activityAdapter() {
        return new ActivityAdapter(activityService, coreActivityService);
    }

    @Bean
    public ClusterAdapter clusterAdapter() {
        return new ClusterAdapter(clusterService);
    }

    @Bean
    public GroupAdapter groupAdapter() {
        return new GroupAdapter(groupService);
    }

    @Bean
    public RegionAdapter regionAdapter() {
        return new RegionAdapter(regionService);
    }

    @Bean
    public StatisticAdapter statisticAdapter() {
        return new StatisticAdapter(statisticHistoryService,
                                    statisticService,
                                    courseStatisticService);
    }

    @Bean
    public UserAdapter userAdapter() {
        return new UserAdapter(userService);
    }
}
