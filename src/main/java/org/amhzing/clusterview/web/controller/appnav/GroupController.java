package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.annotation.LogExecutionTime;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.web.controller.base.AbstractController;
import org.amhzing.clusterview.web.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";
    public static final String GROUPS_MODEL_ATTR = "groups";
    private GroupAdapter groupAdapter;
    private StatisticAdapter statisticAdapter;

    @Autowired
    public GroupController(final GroupAdapter groupAdapter,
                           final StatisticAdapter statisticAdapter) {
        this.groupAdapter = notNull(groupAdapter);
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH)
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               @RequestParam(value = "highlight", required = false) final String activityName,
                               final Model model) {

        final Set<GroupModel> groups = groups(clusterPath.getCluster(), activityName);
        model.addAttribute(GROUPS_MODEL_ATTR, groups);

        final ActivityStatisticModel statistics = statisticAdapter.clusterStats(clusterPath.getCluster());
        model.addAttribute(CountryController.STATISTICS_MODEL_ATTR, statistics);

        return new ModelAndView("/cluster");
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public ModelAndView group(@ModelAttribute @Valid final GroupPath groupPath,
                              final BindingResult bindingResult,
                              final Model model) {

        GroupModel group = GroupModel.empty(groupPath.getGroupId());

        if (!bindingResult.hasErrors()) {
            group = groupAdapter.group(groupPath.getGroupId());
        }

        model.addAttribute("group", group);

        return new ModelAndView("/group");
    }

    private Set<GroupModel> groups(final String clusterId, final String activityName) {
        final Set<GroupModel> groups = groupAdapter.groups(clusterId);

        if (StringUtils.isNotBlank(activityName)) {
            highlightGroups(groups, activityName);
        }

        return groups;
    }

    private void highlightGroups(final Set<GroupModel> groups, final String activityName) {
        final Set<Long> highlightGroups = groupsWithActivity(groups, activityName);

        if (CollectionUtils.isNotEmpty(highlightGroups)) {
            groups.stream()
                  .forEach(group -> group.setHighlight(highlightGroups.contains(group.getId())));
        }
    }

    private Set<Long> groupsWithActivity(final Set<GroupModel> groups, final String activityName) {
        return groups.stream()
                     .filter(group -> membersWithActivity(group.getMembers(), activityName))
                     .map(GroupModel::getId)
                     .collect(Collectors.toSet());
    }

    private boolean membersWithActivity(final List<MemberModel> members, final String activityName) {
        return members.stream()
                      .flatMap(member -> member.getCommitment().getActivities().stream())
                      .anyMatch(activity -> activityName.equalsIgnoreCase(activity.getName()));
    }
}
