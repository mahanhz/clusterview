package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.annotation.LogExecutionTime;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.base.AbstractEditController;
import org.amhzing.clusterview.web.model.GroupModel;
import org.amhzing.clusterview.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.amhzing.clusterview.web.controller.appnav.GroupController.CLUSTER_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupEditController extends AbstractEditController {

    public static final String CREATE_GROUP = "creategroup";

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @ModelAttribute
    public GroupModel groupModel() {
        return new GroupModel();
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/newgroup")
    public String newGroup(@ModelAttribute final GroupPath groupPath) {
        return groupActionView(groupPath);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public String editGroup(@ModelAttribute @Valid final GroupPath groupPath,
                            final BindingResult bindingResult,
                            final Model model) {

        if (bindingResult.hasErrors()) {
            return groupActionView(groupPath);
        }

        groupPath.setAction(String.valueOf(groupPath.getGroupId()));
        groupPath.setMethod(HttpMethod.PUT.name());

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("groupModel", group);

        return groupActionView(groupPath);
    }

    @LogExecutionTime
    @PostMapping(path = CLUSTER_PATH + "/" + CREATE_GROUP)
    public String createGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupActionView(groupPath);
        }

        groupAdapter.createGroup(groupModel, groupPath.getCluster());

        return redirectToClusterView(groupPath);
    }

    @LogExecutionTime
    @PutMapping(path = CLUSTER_PATH + "/{groupId}")
    public String updateGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            groupPath.setAction(String.valueOf(groupPath.getGroupId()));
            groupPath.setMethod(HttpMethod.PUT.name());

            return groupActionView(groupPath);
        }

        groupAdapter.updateGroup(groupModel, groupPath.getCluster());

        return redirectToClusterView(groupPath);
    }

    @LogExecutionTime
    @DeleteMapping(path = CLUSTER_PATH + "/{groupId}")
    public String deleteGroup(@ModelAttribute final GroupPath groupPath,
                              @RequestParam(required = false) final boolean displayConfirmation,
                              final RedirectAttributes redirectAttributes) {

        groupAdapter.deleteGroup(groupPath.getGroupId(), groupPath.getCluster());

        return redirectToClusterView(groupPath);
    }

    private String groupActionView(final GroupPath groupPath) {
        return "/groupaction";
    }

    private String redirectToClusterView(final GroupPath groupPath) {
        return "redirect:/clusterview/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster();
    }

    private String redirectToEditGroupView(final GroupPath groupPath) {
        return "redirect:/clusteredit/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster() + "/" + groupPath.getGroupId();
    }
}
