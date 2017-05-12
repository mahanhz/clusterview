package org.amhzing.clusterview.appui.web.controller.appnav;

import org.amhzing.clusterview.appui.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.appui.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.appui.web.model.CourseStatisticModel;
import org.amhzing.clusterview.appui.web.model.RegionPath;
import org.amhzing.clusterview.appui.web.model.form.CourseStatisticsForm;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.amhzing.clusterview.appui.web.controller.appnav.CountryController.COURSE_STATISTICS_MODEL_ATTR;
import static org.amhzing.clusterview.appui.web.controller.appnav.CountryController.STATISTICS_MODEL_ATTR;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class RegionController extends AbstractController {

    private StatisticAdapter statisticAdapter;

    @Autowired
    public RegionController(final StatisticAdapter statisticAdapter) {
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = "/{country}/{region}")
    public ModelAndView region(@ModelAttribute final RegionPath regionPath,
                               final Model model) {

        final ActivityStatisticModel statistics = statisticAdapter.regionStats(regionPath.getRegion());
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        final List<CourseStatisticModel> courseStatistics = statisticAdapter.regionCourseStats(regionPath.getRegion());
        model.addAttribute(COURSE_STATISTICS_MODEL_ATTR, CourseStatisticsForm.create(courseStatistics));

        return new ModelAndView(regionPath.getCountry() + "/" + regionPath.getRegion());
    }
}
