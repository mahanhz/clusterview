package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.application.*;
import org.amhzing.clusterview.domain.repository.ActivityRepository;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.repository.ClusterStatisticRepository;
import org.amhzing.clusterview.infra.repository.CountryStatisticRepository;
import org.amhzing.clusterview.infra.repository.RegionStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CountryStatisticRepository countryStatisticRepository;

    @Autowired
    private RegionStatisticRepository regionStatisticRepository;

    @Autowired
    private ClusterStatisticRepository clusterStatisticRepository;

    @Bean
    public GroupService groupService() {
        return new DefaultGroupService(groupRepository);
    }

    @Bean
    public ActivityService activityService() {
        return new DefaultActivityService(activityRepository);
    }

    @Bean
    public CountryStatisticService countryStatisticService() {
        return new CountryStatisticService(countryStatisticRepository);
    }

    @Bean
    public RegionStatisticService regionStatisticService() {
        return new RegionStatisticService(regionStatisticRepository);
    }

    @Bean
    public ClusterStatisticService clusterStatisticService() {
        return new ClusterStatisticService(clusterStatisticRepository);
    }
}
