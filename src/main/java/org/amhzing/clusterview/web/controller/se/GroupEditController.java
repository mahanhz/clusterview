package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractEditController;
import org.amhzing.clusterview.web.model.GroupActionPath;
import org.amhzing.clusterview.web.model.GroupModel;
import org.amhzing.clusterview.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.amhzing.clusterview.web.controller.MainController.CLUSTER_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupEditController extends AbstractEditController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @ModelAttribute
    public GroupModel groupModel() {
        return new GroupModel();
    }

    @GetMapping(path = CLUSTER_PATH + "/{groupAction}")
    public ModelAndView newGroup(@ModelAttribute final GroupActionPath groupActionPath) {
        return new ModelAndView(groupActionPath.getCountry() + "/" + groupActionPath.getGroupAction());
    }

//    @GetMapping(path = "/clusteredit/{country}/{region}/{cluster}/editgroup/${groupId}")
//    public ModelAndView editGroup(@ModelAttribute final ClusterPath clusterPath,
//                                 @ModelAttribute final GroupModel groupModel) {
//        return new ModelAndView(clusterPath.getCountry() + "/group-edit");
//    }

    @PostMapping(path = CLUSTER_PATH + "/{groupAction}")
    public String createGroup(@ModelAttribute final GroupActionPath groupActionPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupActionPath.getCountry() + "/" + groupActionPath.getGroupAction();
        }

        groupAdapter.createGroup(groupModel, groupActionPath.getCluster());

        return "redirect:/clusterview/" + groupActionPath.getCountry() + "/" + groupActionPath.getRegion() + "/" + groupActionPath.getCluster();
    }

    @DeleteMapping(path = CLUSTER_PATH + "/{groupId}")
    public String deleteGroup(@ModelAttribute final GroupPath groupPath,
                              @RequestParam(required = false) final boolean displayConfirmation,
                              final RedirectAttributes redirectAttributes) {

        groupAdapter.deleteGroup(groupPath.getGroupId());

        if (displayConfirmation) {
            redirectAttributes.addFlashAttribute("confirmationMessage", "Group has been deleted.");
            return "redirect:/clusterview/" + groupPath.getCountry() + "/confirmation";
        }
        return "redirect:/clusterview/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster();
    }
}
