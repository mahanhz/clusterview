package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.app.web.model.CourseStatisticModel;
import org.amhzing.clusterview.app.web.model.form.CourseStatisticsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class CountryController extends AbstractController {

    public static final String STATISTICS_MODEL_ATTR = "statistics";
    public static final String COURSE_STATISTICS_MODEL_ATTR = "courseStatistics";

    private StatisticAdapter statisticAdapter;

    @Autowired
    public CountryController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}")
    public ModelAndView country(@ModelAttribute @PathVariable final String country,
                                final Model model) {

        final ActivityStatisticModel statistics = statisticAdapter.countryStats(country);
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        final List<CourseStatisticModel> courseStatistics = statisticAdapter.countryCourseStats(country);
        model.addAttribute(COURSE_STATISTICS_MODEL_ATTR, CourseStatisticsForm.create(courseStatistics));

        return new ModelAndView(country + "/index");
    }
}
