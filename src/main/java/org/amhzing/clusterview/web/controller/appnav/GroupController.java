package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.controller.base.AbstractController;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.ClusterPath;
import org.amhzing.clusterview.web.model.GroupModel;
import org.amhzing.clusterview.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    private GroupAdapter groupAdapter;
    private StatisticAdapter statisticAdapter;

    @Autowired
    public GroupController(final GroupAdapter groupAdapter,
                           final StatisticAdapter statisticAdapter) {
        this.groupAdapter = notNull(groupAdapter);
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @GetMapping(path = CountryController.CLUSTER_PATH)
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               final Model model) {

        final Set<GroupModel> groups = groupAdapter.groups(clusterPath.getCluster());
        model.addAttribute("groups", groups);

        final ActivityStatisticModel statistics = statisticAdapter.clusterStats(clusterPath.getCluster());
        model.addAttribute(CountryController.STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(clusterPath.getCountry() + "/cluster");
    }

    @GetMapping(path = CountryController.CLUSTER_PATH + "/{groupId}")
    public ModelAndView group(@ModelAttribute @Valid final GroupPath groupPath,
                              final BindingResult bindingResult,
                              final Model model) {

        GroupModel group = GroupModel.empty(groupPath.getGroupId());

        if (!bindingResult.hasErrors()) {
            group = groupAdapter.group(groupPath.getGroupId());

        }

        model.addAttribute("group", group);

        return new ModelAndView(groupPath.getCountry() + "/group");
    }
}