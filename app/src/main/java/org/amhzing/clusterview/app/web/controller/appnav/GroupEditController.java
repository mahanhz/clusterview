package org.amhzing.clusterview.app.web.controller.appnav;

import org.amhzing.clusterview.app.web.controller.base.AbstractEditController;
import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.controller.appnav.GroupEditRestController;
import org.amhzing.clusterview.backend.web.model.GroupModel;
import org.amhzing.clusterview.backend.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.amhzing.clusterview.app.web.controller.appnav.GroupController.CLUSTER_PATH;
import static org.amhzing.clusterview.backend.web.model.GroupPath.CREATE_GROUP;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupEditController extends AbstractEditController {

    private GroupEditRestController groupEditRestController;

    @Autowired
    public GroupEditController(final GroupEditRestController groupEditRestController) {
        this.groupEditRestController = notNull(groupEditRestController);
    }

    @ModelAttribute
    public GroupModel groupModel() {
        return new GroupModel();
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/newgroup")
    public String newGroup(@ModelAttribute final GroupPath groupPath) {
        return groupActionView();
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public String editGroup(@ModelAttribute @Valid final GroupPath groupPath,
                            final BindingResult bindingResult,
                            final Model model) {

        if (bindingResult.hasErrors()) {
            return groupActionView();
        }

        groupPath.setAction(groupPath.getObfuscatedGroupId());
        groupPath.setMethod(HttpMethod.PUT.name());

        final GroupModel group = groupEditRestController.editGroup(groupPath);

        model.addAttribute("groupModel", group);

        return groupActionView();
    }

    @LogExecutionTime
    @PostMapping(path = CLUSTER_PATH + "/" + CREATE_GROUP)
    public String createGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupActionView();
        }

        groupEditRestController.createGroup(groupPath, groupModel);

        return redirectToClusterView(groupPath);
    }

    @LogExecutionTime
    @PutMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public String updateGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            groupPath.setAction(groupPath.getObfuscatedGroupId());
            groupPath.setMethod(HttpMethod.PUT.name());

            return groupActionView();
        }

        groupEditRestController.updateGroup(groupPath, groupModel);

        return redirectToClusterView(groupPath);
    }

    @LogExecutionTime
    @DeleteMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public String deleteGroup(@ModelAttribute final GroupPath groupPath,
                              @RequestParam(required = false) final boolean displayConfirmation,
                              final RedirectAttributes redirectAttributes) {

        groupEditRestController.deleteGroup(groupPath);

        return redirectToClusterView(groupPath);
    }

    private String groupActionView() {
        return "/groupaction";
    }

    private String redirectToClusterView(final GroupPath groupPath) {
        return "redirect:/clusterview/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster();
    }
}
