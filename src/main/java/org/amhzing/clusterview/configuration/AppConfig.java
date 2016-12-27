package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.application.*;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Country;
import org.amhzing.clusterview.domain.model.Region;
import org.amhzing.clusterview.domain.model.statistic.ActivityStatistic;
import org.amhzing.clusterview.domain.repository.*;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;
import org.amhzing.clusterview.user.UserServletFilter;
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
    public StatisticService statisticService() {
        return new DefaultStatisticService(countryStatisticRepository,
                                           regionStatisticRepository,
                                           clusterStatisticRepository);
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
