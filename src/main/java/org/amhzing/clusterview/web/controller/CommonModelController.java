package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.user.DefaultUserDetails;
import org.amhzing.clusterview.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.model.ActivityModel;
import org.amhzing.clusterview.web.model.ClusterNameModel;
import org.amhzing.clusterview.web.model.CoreActivityModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@ControllerAdvice(basePackages = { "org.amhzing.clusterview.web.controller" })
public class CommonModelController {

    private StatisticAdapter statisticAdapter;
    private ActivityAdapter activityAdapter;
    private CoreActivityAdapter coreActivityAdapter;

    @Autowired
    public CommonModelController(final StatisticAdapter statisticAdapter,
                                 final ActivityAdapter activityAdapter,
                                 final CoreActivityAdapter coreActivityAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
        this.activityAdapter = notNull(activityAdapter);
        this.coreActivityAdapter = notNull(coreActivityAdapter);
    }

    @ModelAttribute("userCountry")
    public String userCountry(final Authentication authentication) {
        if (authentication != null) {
            final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();

            // TODO - this assumes that every user belongs to only one country
            if (userDetails != null && CollectionUtils.isNotEmpty(userDetails.getCountries())) {
                return userDetails.getCountries().iterator().next().getId();
            }
        }

        return "XX";
    }

    @ModelAttribute("activityValues")
    public List<ActivityModel> activityModel() {
        return activityAdapter.activities();
    }

    @ModelAttribute("coreActivityValues")
    public List<CoreActivityModel> coreActivityModel() {
        return coreActivityAdapter.coreActivities();
    }

    @ModelAttribute("clusterValues")
    public List<ClusterNameModel> clusters() {
        // TODO - currently hardcoded
        return statisticAdapter.clusters("se");
    }
}
