package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.model.ClusterPath;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupEditController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @ModelAttribute
    public GroupModel groupModel() {
        return new GroupModel();
    }

    @GetMapping(path = "/clusteredit/{country}/{region}/{cluster}/newgroup")
    public ModelAndView newGroup(@ModelAttribute final ClusterPath clusterPath) {
        return new ModelAndView(clusterPath.getCountry() + "/group-new");
    }

//    @GetMapping(path = "/clusteredit/{country}/{region}/{cluster}/editgroup/${groupId}")
//    public ModelAndView editGroup(@ModelAttribute final ClusterPath clusterPath,
//                                 @ModelAttribute final GroupModel groupModel) {
//        return new ModelAndView(clusterPath.getCountry() + "/group-edit");
//    }

    @PostMapping(path = "/clusteredit/{country}/{region}/{cluster}/creategroup")
    public ModelAndView createGroup(@ModelAttribute final ClusterPath clusterPath,
                                    @ModelAttribute @Valid final GroupModel groupModel,
                                    final BindingResult bindingResult,
                                    final Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("groupModel", groupModel);
            return new ModelAndView(clusterPath.getCountry() + "/group-new");
        }

        groupAdapter.createGroup(groupModel, clusterPath.getCluster());

        return new ModelAndView("redirect:/clusterview/" + clusterPath.getCountry() + "/" + clusterPath.getRegion() + "/" + clusterPath.getCluster());
    }
}
