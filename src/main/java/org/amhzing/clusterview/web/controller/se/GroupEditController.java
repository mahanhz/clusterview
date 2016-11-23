package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.model.ClusterPath;
import org.amhzing.clusterview.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @GetMapping(path = "/clusteredit/{country}/{region}/{cluster}/group-new")
    public ModelAndView newGroup(@ModelAttribute final ClusterPath clusterPath,
                                 @ModelAttribute final GroupModel groupModel,
                                 final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RuntimeException("New group failed due to: " + bindingResult.getFieldError());
        }

        return new ModelAndView(clusterPath.getCountry() + "/group-new");
    }

    @PostMapping(path = "/clusteredit/{country}/{region}/{cluster}/group-create")
    public String createGroup(@ModelAttribute final ClusterPath clusterPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            final FieldError fieldError = bindingResult.getFieldError();

            return clusterPath.getCountry() + "/group-new";
        }

        groupAdapter.createGroup(groupModel, clusterPath.getCluster());

        return "redirect:/clusterview/" + clusterPath.getCountry() + "/" + clusterPath.getRegion() + "/" + clusterPath.getCluster();
    }
}
