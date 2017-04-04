package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.appui.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.appui.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.appui.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.appui.web.model.ActivityModel;
import org.amhzing.clusterview.appui.web.model.ClusterNameModel;
import org.amhzing.clusterview.appui.web.model.CoreActivityModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.user.UserUtil.USER_COUNTRY;
import static org.apache.commons.lang3.Validate.notNull;

@ControllerAdvice(basePackageClasses = { CommonModelController.class })
public class CommonModelController {

    public static final String ACTIVITY_VALUES_MODEL = "activityValues";
    public static final String CORE_ACTIVITY_VALUES_MODEL = "coreActivityValues";
    public static final String CLUSTER_VALUES_MODEL = "clusterValues";

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

    @ModelAttribute(ACTIVITY_VALUES_MODEL)
    public List<ActivityModel> activityModel() {
        return activityAdapter.activities();
    }

    @ModelAttribute(CORE_ACTIVITY_VALUES_MODEL)
    public List<CoreActivityModel> coreActivityModel() {
        return coreActivityAdapter.coreActivities();
    }

    @ModelAttribute(CLUSTER_VALUES_MODEL)
    public List<ClusterNameModel> clusters(final HttpServletRequest request) {
        final String userCountry = (String) request.getAttribute(USER_COUNTRY);

        return StringUtils.isNotBlank(userCountry) ? statisticAdapter.clusters(userCountry) : emptyList();
    }
}
