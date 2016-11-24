package org.amhzing.clusterview.web.controller.se;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static org.amhzing.clusterview.web.controller.MainController.CLUSTER_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @GetMapping(path = CLUSTER_PATH)
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               final Model model) {

        final Set<GroupModel> groups = groupAdapter.groups(clusterPath.getCluster());
        model.addAttribute("groups", groups);

        return new ModelAndView(clusterPath.getCountry() + "/cluster");
    }

    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public ModelAndView group(@ModelAttribute final GroupPath groupPath,
                              final Model model) {

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("group", group);

        return new ModelAndView(groupPath.getCountry() + "/group");
    }

//    @DeleteMapping(path = PATH_PREFIX + "/{groupId}")
//    public ModelAndView deleteGroup(@ModelAttribute final GroupPath groupPath) {
//
//        groupAdapter.deleteGroup(groupPath.getGroupId());
//
//        return new ModelAndView("redirect:" + groupPath.getCountry() + "/cluster");
//    }

    @PutMapping(path = CLUSTER_PATH + "/{groupId}")
    public ModelAndView updateGroup(@ModelAttribute final ClusterPath clusterPath) {

        final LocationModel locationModel = LocationModel.create(267, 277);
        final ActivityModel activityModel2 = ActivityModel.create("jyg", "Study Circle");
        final ActivityModel activityModel3 = ActivityModel.create("dm", "Study Circle");
        final CapabilityModel capabilityModel = CapabilityModel.create(ImmutableList.of(activityModel2));
        final CommitmentModel commitmentModel = CommitmentModel.create(ImmutableList.of(activityModel3));
        final NameModel nameModel = NameModel.create("What", "The", "Did", "Junior");
        final MemberModel memberModel = MemberModel.create(445, nameModel, capabilityModel, commitmentModel);
        final GroupModel groupModel1 = GroupModel.create(904, ImmutableList.of(memberModel), locationModel);

//        if (bindingResult.hasErrors()) {
//            throw new RuntimeException("Could not create group due to: " + bindingResult.getFieldError());
//        }

        groupAdapter.updateGroup(groupModel1);

        return new ModelAndView("redirect:" + clusterPath.getCountry() + "/cluster");
    }
}
