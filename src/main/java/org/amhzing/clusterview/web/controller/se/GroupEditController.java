package org.amhzing.clusterview.web.controller.se;

import org.amhzing.clusterview.cache.CacheEvicter;
import org.amhzing.clusterview.web.adapter.ActivityAdapter;
import org.amhzing.clusterview.web.adapter.CoreActivityAdapter;
import org.amhzing.clusterview.web.adapter.GroupAdapter;
import org.amhzing.clusterview.web.controller.AbstractEditController;
import org.amhzing.clusterview.web.model.ActivityModel;
import org.amhzing.clusterview.web.model.CoreActivityModel;
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
import java.util.List;

import static org.amhzing.clusterview.web.controller.MainController.CLUSTER_PATH;
import static org.apache.commons.lang3.Validate.notNull;

@Controller
public class GroupEditController extends AbstractEditController {

    public static final String CREATE_GROUP = "creategroup";

    private GroupAdapter groupAdapter;
    private ActivityAdapter activityAdapter;
    private CoreActivityAdapter coreActivityAdapter;
    private CacheEvicter cacheEvicter;

    @Autowired
    public GroupEditController(final GroupAdapter groupAdapter,
                               final ActivityAdapter activityAdapter,
                               final CoreActivityAdapter coreActivityAdapter,
                               final CacheEvicter cacheEvicter) {
        this.groupAdapter = notNull(groupAdapter);
        this.activityAdapter = notNull(activityAdapter);
        this.coreActivityAdapter = notNull(coreActivityAdapter);
        this.cacheEvicter = notNull(cacheEvicter);
    }

    @ModelAttribute
    public GroupModel groupModel() {
        return new GroupModel();
    }

    @ModelAttribute("activityValues")
    public List<ActivityModel> activityModel() {
        return activityAdapter.activities();
    }

    @ModelAttribute("coreActivityValues")
    public List<CoreActivityModel> coreActivityModel() {
        return coreActivityAdapter.coreActivities();
    }

    @GetMapping(path = CLUSTER_PATH + "/newgroup")
    public String newGroup(@ModelAttribute final GroupPath groupPath) {
        return groupActionView(groupPath);
    }

    @GetMapping(path = CLUSTER_PATH + "/{groupId}")
    public String editGroup(@ModelAttribute final GroupPath groupPath,
                            final Model model) {

        groupPath.setAction(String.valueOf(groupPath.getGroupId()));
        groupPath.setMethod(HttpMethod.PUT.name());

        final GroupModel group = groupAdapter.group(groupPath.getGroupId());

        model.addAttribute("groupModel", group);

        return groupActionView(groupPath);
    }

    @PostMapping(path = CLUSTER_PATH + "/" + CREATE_GROUP)
    public String createGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return groupActionView(groupPath);
        }

        groupAdapter.createGroup(groupModel, groupPath.getCluster());

        clearCaches();

        return redirectToClusterView(groupPath);
    }

    @PutMapping(path = CLUSTER_PATH + "/{groupId}")
    public String updateGroup(@ModelAttribute final GroupPath groupPath,
                              @ModelAttribute @Valid final GroupModel groupModel,
                              final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            groupPath.setAction(String.valueOf(groupPath.getGroupId()));
            groupPath.setMethod(HttpMethod.PUT.name());

            return groupActionView(groupPath);
        }

        groupAdapter.updateGroup(groupModel);

        clearCaches();

        return redirectToClusterView(groupPath);
    }

    @DeleteMapping(path = CLUSTER_PATH + "/{groupId}")
    public String deleteGroup(@ModelAttribute final GroupPath groupPath,
                              @RequestParam(required = false) final boolean displayConfirmation,
                              final RedirectAttributes redirectAttributes) {

        groupAdapter.deleteGroup(groupPath.getGroupId());

        clearCaches();

        return redirectToClusterView(groupPath);
    }

    private String groupActionView(final GroupPath groupPath) {
        return groupPath.getCountry() + "/groupaction";
    }

    private String redirectToClusterView(final GroupPath groupPath) {
        return "redirect:/clusterview/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster();
    }

    private String redirectToEditGroupView(final GroupPath groupPath) {
        return "redirect:/clusteredit/" + groupPath.getCountry() + "/" + groupPath.getRegion() + "/" + groupPath.getCluster() + "/" + groupPath.getGroupId();
    }

    private void clearCaches() {
        cacheEvicter.clearStatsCache();
        cacheEvicter.clearGroupsCache();
    }
}
