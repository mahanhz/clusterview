package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ClusterPath;
import org.amhzing.clusterview.app.web.model.CourseStatisticModel;
import org.amhzing.clusterview.app.web.model.form.CourseStatisticsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

import static org.amhzing.clusterview.appui.web.controller.appnav.GroupController.CLUSTER_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class ClusterEditController extends AbstractEditController {

    public static final String COURSE_STATS = "/coursestats";
    public static final String COURSE_STATS_MODEL = "courseStats";
    private StatisticAdapter statisticAdapter;

    @Autowired
    public ClusterEditController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + COURSE_STATS)
    public String courseStats(@ModelAttribute @Valid final ClusterPath clusterPath,
                            final Model model) {

        final List<CourseStatisticModel> courseStatistics = statisticAdapter.clusterCourseStats(clusterPath.getCluster());

        model.addAttribute(COURSE_STATS_MODEL, CourseStatisticsForm.create(courseStatistics));

        return courseStatsView();
    }

    @LogExecutionTime
    @PostMapping(path = CLUSTER_PATH + COURSE_STATS)
    public String saveCourseStats(@ModelAttribute final ClusterPath clusterPath,
                              @ModelAttribute(COURSE_STATS_MODEL) @Valid final CourseStatisticsForm form,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return courseStatsView();
        }

        statisticAdapter.saveCourseStats(clusterPath.getCluster(), form);

        return redirectToClusterView(clusterPath);
    }

    private String courseStatsView() {
        return "/course-stats";
    }

    private String redirectToClusterView(final ClusterPath clusterPath) {
        return "redirect:/clusterview/" + clusterPath.getCountry() + "/" + clusterPath.getRegion() + "/" + clusterPath.getCluster();
    }
}
