package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.application.ActivityService;
import org.amhzing.clusterview.application.DefaultActivityService;
import org.amhzing.clusterview.application.DefaultGroupService;
import org.amhzing.clusterview.application.GroupService;
import org.amhzing.clusterview.domain.repository.ActivityRepository;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Bean
    public GroupService groupService() {
        return new DefaultGroupService(groupRepository);
    }

    @Bean
    public ActivityService activityService() {
        return new DefaultActivityService(activityRepository);
    }
}
