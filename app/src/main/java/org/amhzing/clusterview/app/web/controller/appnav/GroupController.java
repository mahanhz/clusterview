package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.web.controller.base.AbstractController;
import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.GroupRestController;
import org.amhzing.clusterview.backend.web.model.ClusterPath;
import org.amhzing.clusterview.backend.web.model.GroupModel;
import org.amhzing.clusterview.backend.web.model.GroupPath;
import org.amhzing.clusterview.backend.web.model.GroupsStatisticsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";
    public static final String GROUPS_MODEL_ATTR = "groups";

    private GroupRestController groupRestController;

    @Autowired
    public GroupController(final GroupRestController groupRestController) {
        this.groupRestController = notNull(groupRestController);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH)
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               @RequestParam(value = "highlight", required = false) final String activityName,
                               final Model model) {

        final GroupsStatisticsModel groups = groupRestController.groups(clusterPath, activityName);

        model.addAttribute(GROUPS_MODEL_ATTR, groups.getGroups());
        model.addAttribute(CountryController.STATISTICS_MODEL_ATTR, groups.getStatistics());

        return new ModelAndView("/cluster");
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ModelAndView group(@ModelAttribute @Valid final GroupPath groupPath,
                              final BindingResult bindingResult,
                              final Model model) {

        GroupModel group = GroupModel.empty(groupPath.getObfuscatedGroupId());

        if (!bindingResult.hasErrors()) {
            group = groupRestController.group(groupPath, bindingResult);
        }

        model.addAttribute("group", group);

        return new ModelAndView("/group");
    }
}
