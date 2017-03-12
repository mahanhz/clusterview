package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.web.adapter.GroupAdapter;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractEditRestController;
import org.amhzing.clusterview.app.web.model.GroupModel;
import org.amhzing.clusterview.app.web.model.GroupPath;
import org.amhzing.clusterview.app.web.model.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.amhzing.clusterview.app.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.app.web.model.GroupPath.CREATE_GROUP;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class GroupEditRestController extends AbstractEditRestController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditRestController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}")
    public GroupModel editGroup(@Valid final GroupPath groupPath) {

        return groupAdapter.group(groupPath.getObfuscatedGroupId());
    }

    @LogExecutionTime
    @PostMapping(path = GroupRestController.CLUSTER_PATH + "/" + CREATE_GROUP, consumes = APPLICATION_JSON_V1_VALUE)
    public SimpleResponse createGroup(@ModelAttribute final GroupPath groupPath,
                                      @RequestBody @Valid final GroupModel groupModel) {

        groupAdapter.createGroup(groupModel, groupPath.getCluster());

        return SimpleResponse.create("CREATED");
    }

    @LogExecutionTime
    @PutMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}", consumes = APPLICATION_JSON_V1_VALUE)
    public SimpleResponse updateGroup(@ModelAttribute final GroupPath groupPath,
                                      @RequestBody @Valid final GroupModel groupModel) {

        groupAdapter.updateGroup(groupModel, groupPath.getCluster());

        return SimpleResponse.create(groupPath.getObfuscatedGroupId(), "UPDATED");
    }

    @LogExecutionTime
    @DeleteMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}")
    public SimpleResponse deleteGroup(@ModelAttribute final GroupPath groupPath) {

        groupAdapter.deleteGroup(groupPath.getObfuscatedGroupId(), groupPath.getCluster());

        return SimpleResponse.create(groupPath.getObfuscatedGroupId(), "DELETED");
    }
}
