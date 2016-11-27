package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractEditController;
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

import static org.amhzing.clusterview.web.controller.MainController.CLUSTER_PATH;
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

    @GetMapping(path = CLUSTER_PATH + "/groupaction")
    public String groupAction(@ModelAttribute final GroupPath groupPath) {
        return groupPath.getCountry() + "/groupaction";
    }

    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public String editGroup(@ModelAttribute final GroupPath groupPath,
                            final Model model) {

        groupPath.setAction(String.valueOf(groupPath.getGroupId()));
        groupPath.setMethod(HttpMethod.PUT.name());

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("groupModel", group);

        return groupAction(groupPath);
    }

    @PostMapping(path = CLUSTER_PATH + "/" + CREATE_GROUP)
    public String createGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupAction(groupPath);
        }

        groupAdapter.createGroup(groupModel, groupPath.getCluster());

        return redirectToClusterView(groupPath);
    }

    @PutMapping(path = CLUSTER_PATH + "/{groupId}")
    public String updateGroup(@ModelAttribute final GroupPath groupPath,
                            @ModelAttribute @Valid final GroupModel groupModel,
                            final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupAction(groupPath);
        }

        groupAdapter.updateGroup(groupModel);

        return redirectToClusterView(groupPath);
    }

    @DeleteMapping(path = CLUSTER_PATH + "/{groupId}")
    public String deleteGroup(@ModelAttribute final GroupPath groupPath,
                              @RequestParam(required = false) final boolean displayConfirmation,
                              final RedirectAttributes redirectAttributes) {

        groupAdapter.deleteGroup(groupPath.getGroupId());

        return redirectToClusterView(groupPath);
    }

    private String redirectToConfirmationView(final GroupPath groupPath) {
        return "redirect:/clusterview/" + groupPath.getCountry() + "/confirmation";
    }

    private String redirectToClusterView(final GroupPath groupPath) {
        return "redirect:/clusterview/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster();
    }
}
