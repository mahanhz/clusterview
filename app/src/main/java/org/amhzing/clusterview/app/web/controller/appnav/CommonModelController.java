package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.backend.web.controller.appnav.ReferenceDataRestController;
import org.amhzing.clusterview.backend.web.model.ActivityModel;
import org.amhzing.clusterview.backend.web.model.ClusterNameModel;
import org.amhzing.clusterview.backend.web.model.CoreActivityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notNull;

@ControllerAdvice(basePackageClasses = { CommonModelController.class })
public class CommonModelController {

    public static final String USER_COUNTRY = "userCountry";
    public static final String ACTIVITY_VALUES_MODEL = "activityValues";
    public static final String CORE_ACTIVITY_VALUES_MODEL = "coreActivityValues";
    public static final String CLUSTER_VALUES_MODEL = "clusterValues";

    private ReferenceDataRestController referenceDataRestController;

    @Autowired
    public CommonModelController(final ReferenceDataRestController referenceDataRestController) {
        this.referenceDataRestController = notNull(referenceDataRestController);
    }

    @ModelAttribute(ACTIVITY_VALUES_MODEL)
    public List<ActivityModel> activityModel() {
        return referenceDataRestController.activities();
    }

    @ModelAttribute(CORE_ACTIVITY_VALUES_MODEL)
    public List<CoreActivityModel> coreActivityModel() {
        return referenceDataRestController.coreActivities();
    }

    @ModelAttribute(CLUSTER_VALUES_MODEL)
    public List<ClusterNameModel> clusters(final HttpSession httpSession) {
        final String userCountry = (String) httpSession.getAttribute(USER_COUNTRY);

        return isNotBlank(userCountry) ? referenceDataRestController.clusters(userCountry) : emptyList();
    }
}
