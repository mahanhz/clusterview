package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractController;
import org.amhzing.clusterview.web.model.GroupModel;
import org.amhzing.clusterview.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupController extends AbstractController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @GetMapping(path = "/{country}/{region}/{cluster}/{groupId}")
    public ModelAndView group(@ModelAttribute @Valid final GroupPath groupPath,
                              final BindingResult bindingResult,
                              final Model model) {

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("group", group);

        return new ModelAndView(groupPath.getCountry() + "/group");
    }
}
