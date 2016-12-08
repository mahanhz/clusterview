package org.amhzing.clusterview.web.controller;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.model.ActivityStatisticModel;
import org.amhzing.clusterview.web.model.ClusterPath;
import org.amhzing.clusterview.web.model.GroupModel;
import org.amhzing.clusterview.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static org.amhzing.clusterview.web.controller.MainController.CLUSTER_PATH;
import static org.amhzing.clusterview.web.controller.MainController.STATISTICS_MODEL_ATTR;
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

    @GetMapping(path = CLUSTER_PATH)
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               final Model model) {

        final Set<GroupModel> groups = groupAdapter.groups(clusterPath.getCluster());
        model.addAttribute("groups", groups);

        final ActivityStatisticModel statistics = statisticAdapter.clusterStats(clusterPath.getCluster());
        model.addAttribute(STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView(clusterPath.getCountry() + "/cluster");
    }

    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public ModelAndView group(@ModelAttribute final GroupPath groupPath,
                              final Model model) {

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("group", group);

        return new ModelAndView(groupPath.getCountry() + "/group");
    }
}