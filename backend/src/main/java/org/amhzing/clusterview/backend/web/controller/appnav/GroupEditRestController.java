package org.amhzing.clusterview.backend.web.controller.appnav;

import org.amhzing.clusterview.backend.annotation.LogExecutionTime;
import org.amhzing.clusterview.backend.web.adapter.GroupAdapter;
import org.amhzing.clusterview.backend.web.controller.base.AbstractEditRestController;
import org.amhzing.clusterview.backend.web.model.GroupModel;
import org.amhzing.clusterview.backend.web.model.GroupPath;
import org.amhzing.clusterview.backend.web.model.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.amhzing.clusterview.backend.web.MediaType.APPLICATION_JSON_V1;
import static org.amhzing.clusterview.backend.web.controller.appnav.GroupRestController.CLUSTER_PATH;
import static org.amhzing.clusterview.backend.web.model.GroupPath.CREATE_GROUP;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class GroupEditRestController extends AbstractEditRestController {

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditRestController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public GroupModel editGroup(@Valid final GroupPath groupPath) {

        return groupAdapter.group(groupPath.getObfuscatedGroupId());
    }

    @LogExecutionTime
    @PostMapping(path = CLUSTER_PATH + "/" + CREATE_GROUP, consumes = APPLICATION_JSON_V1)
    public SimpleResponse createGroup(@ModelAttribute final GroupPath groupPath,
                                      @RequestBody @Valid final GroupModel groupModel) {

        groupAdapter.createGroup(groupModel, groupPath.getCluster());

        return SimpleResponse.create("CREATED");
    }

    @LogExecutionTime
    @PutMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}", consumes = APPLICATION_JSON_V1)
    public SimpleResponse updateGroup(@ModelAttribute final GroupPath groupPath,
                                      @RequestBody @Valid final GroupModel groupModel) {

        groupAdapter.updateGroup(groupModel, groupPath.getCluster());

        return SimpleResponse.create(groupPath.getObfuscatedGroupId(), "UPDATED");
    }

    @LogExecutionTime
    @DeleteMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public SimpleResponse deleteGroup(@ModelAttribute final GroupPath groupPath) {

        groupAdapter.deleteGroup(groupPath.getObfuscatedGroupId(), groupPath.getCluster());

        return SimpleResponse.create(groupPath.getObfuscatedGroupId(), "DELETED");
    }
}
