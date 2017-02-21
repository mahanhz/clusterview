package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.StatisticEditRestController;
import org.amhzing.clusterview.backend.web.model.ClusterNameModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
@RequestMapping(path = "/statsedit")
public class StatisticEditController {

    private StatisticEditRestController statisticEditRestController;

    @Autowired
    public StatisticEditController(final StatisticEditRestController statisticEditRestController) {
        this.statisticEditRestController = notNull(statisticEditRestController);
    }

    @LogExecutionTime
    @PostMapping(path = StatisticController.HISTORY + "/{country}")
    public String saveHistory(@ModelAttribute @PathVariable final String country,
                              @ModelAttribute @Valid final ClusterNameModel clusterNameModel,
                              final BindingResult bindingResult,
                              final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return statsView(country);
        }

        final String cluster = clusterNameModel.getName();

        statisticEditRestController.saveHistory(country, clusterNameModel);

        redirectAttributes.addFlashAttribute("statsHistoryMessage", cluster + " history saved");

        return redirectToStatsView(country);
    }

    private String statsView(final String country) {
        return "/stats-history";
    }

    private String redirectToStatsView(final String country) {
        return "redirect:/statsview" + StatisticController.HISTORY + "/" + country;
    }
}
