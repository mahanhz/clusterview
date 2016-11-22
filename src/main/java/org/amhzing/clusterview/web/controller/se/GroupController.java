package org.amhzing.clusterview.web.controller.se;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @GetMapping(path = "/{country}/{region}/{cluster}")
    public ModelAndView groups(@ModelAttribute final ClusterPath clusterPath,
                               final Model model) {

        final Set<GroupModel> groups = groupAdapter.groups(clusterPath.getCluster());
        model.addAttribute("groups", groups);

        return new ModelAndView(clusterPath.getCountry() + "/cluster");
    }

    @GetMapping(path = "/{country}/{region}/{cluster}/{groupId}")
    public ModelAndView group(@ModelAttribute final GroupPath groupPath,
                              final Model model) {

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("group", group);

        return new ModelAndView(groupPath.getCountry() + "/group");
    }

    @PostMapping(path = "/{country}/{region}/{cluster}/create-group")
    public ModelAndView createGroup(@ModelAttribute final ClusterPath clusterPath) {

        final LocationModel locationModel = LocationModel.create(345.0, 192.47);
        final ActivityModel activityModel1 = ActivityModel.create("sc", "Study Circle");
        final ActivityModel activityModel2 = ActivityModel.create("jyg", "Study Circle");
        final ActivityModel activityModel3 = ActivityModel.create("hv", "Study Circle");
        final CapabilityModel capabilityModel = CapabilityModel.create(ImmutableSet.of(activityModel1, activityModel2));
        final CommitmentModel commitmentModel = CommitmentModel.create(ImmutableSet.of(activityModel3));
        final NameModel nameModel = NameModel.create("Say", "The", "Man", "Junior");
        final MemberModel memberModel = MemberModel.create(999, nameModel, capabilityModel, commitmentModel);
        final GroupModel groupModel1 = GroupModel.create(888, ImmutableSet.of(memberModel), locationModel);

//        if (bindingResult.hasErrors()) {
//            throw new RuntimeException("Could not create group due to: " + bindingResult.getFieldError());
//        }

        groupAdapter.createGroup(groupModel1, clusterPath.getCluster());

        return new ModelAndView("redirect:" + clusterPath.getCountry() + "/cluster");
    }

    @DeleteMapping(path = "/{country}/{region}/{cluster}/{groupId}")
    public ModelAndView deleteGroup(@ModelAttribute final GroupPath groupPath) {

        groupAdapter.deleteGroup(groupPath.getGroupId());

        return new ModelAndView("redirect:" + groupPath.getCountry() + "/cluster");
    }

    @PutMapping(path = "/{country}/{region}/{cluster}/{groupId}")
    public ModelAndView updateGroup(@ModelAttribute final ClusterPath clusterPath) {

        final LocationModel locationModel = LocationModel.create(267.0, 277.47);
        final ActivityModel activityModel2 = ActivityModel.create("jyg", "Study Circle");
        final ActivityModel activityModel3 = ActivityModel.create("dm", "Study Circle");
        final CapabilityModel capabilityModel = CapabilityModel.create(ImmutableSet.of(activityModel2));
        final CommitmentModel commitmentModel = CommitmentModel.create(ImmutableSet.of(activityModel3));
        final NameModel nameModel = NameModel.create("What", "The", "Did", "Junior");
        final MemberModel memberModel = MemberModel.create(445, nameModel, capabilityModel, commitmentModel);
        final GroupModel groupModel1 = GroupModel.create(904, ImmutableSet.of(memberModel), locationModel);

//        if (bindingResult.hasErrors()) {
//            throw new RuntimeException("Could not create group due to: " + bindingResult.getFieldError());
//        }

        groupAdapter.updateGroup(groupModel1);

        return new ModelAndView("redirect:" + clusterPath.getCountry() + "/cluster");
    }
}