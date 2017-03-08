package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.GroupAdapter;
import org.amhzing.clusterview.app.web.adapter.StatisticAdapter;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
import org.amhzing.clusterview.app.web.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class GroupRestController extends AbstractRestController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";

    private GroupAdapter groupAdapter;
    private StatisticAdapter statisticAdapter;

    @Autowired
    public GroupRestController(final GroupAdapter groupAdapter,
                               final StatisticAdapter statisticAdapter) {
        this.groupAdapter = notNull(groupAdapter);
        this.statisticAdapter = notNull(statisticAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH)
    public GroupsStatisticsModel groups(@ModelAttribute final ClusterPath clusterPath,
                                        @RequestParam(value = "highlight", required = false) final String activityName) {

        final Set<GroupModel> groups = groups(clusterPath.getCluster(), activityName);
        final ActivityStatisticModel statistics = statisticAdapter.clusterStats(clusterPath.getCluster());

        return GroupsStatisticsModel.create(groups, statistics);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public GroupModel group(@ModelAttribute @Valid final GroupPath groupPath,
                            final BindingResult bindingResult) {

        GroupModel group = GroupModel.empty(groupPath.getObfuscatedGroupId());

        if (!bindingResult.hasErrors()) {
            group = groupAdapter.group(groupPath.getObfuscatedGroupId());
        }

        return group;
    }

    private Set<GroupModel> groups(final String clusterId, final String activityName) {
        final Set<GroupModel> groups = groupAdapter.groups(clusterId);

        if (StringUtils.isNotBlank(activityName)) {
            highlightGroups(groups, activityName);
        }

        return groups;
    }

    private void highlightGroups(final Set<GroupModel> groups, final String activityName) {
        final Set<String> highlightGroups = groupsWithActivity(groups, activityName);

        if (CollectionUtils.isNotEmpty(highlightGroups)) {
            groups.stream()
                  .forEach(group -> group.setHighlight(highlightGroups.contains(group.getObfuscatedId())));
        }
    }

    private Set<String> groupsWithActivity(final Set<GroupModel> groups, final String activityName) {
        return groups.stream()
                     .filter(group -> membersWithActivity(group.getMembers(), activityName))
                     .map(GroupModel::getObfuscatedId)
                     .collect(Collectors.toSet());
    }

    private boolean membersWithActivity(final List<MemberModel> members, final String activityName) {
        return members.stream()
                      .flatMap(member -> member.getCommitment().getActivities().stream())
                      .anyMatch(activity -> activityName.equalsIgnoreCase(activity.getName()));
    }
}
