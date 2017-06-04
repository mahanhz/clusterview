package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.GroupAdapter;
import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.web.controller.base.AbstractEditRestController;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.amhzing.clusterview.web.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.clusterLink;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class GroupEditRestController extends AbstractEditRestController {

    public static final String CREATE_GROUP = "creategroup";

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupEditRestController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @LogExecutionTime
    @PostMapping(path = GroupRestController.CLUSTER_PATH + "/" + CREATE_GROUP, consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> createGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @RequestBody @Valid final GroupDTO groupDto) {

        groupAdapter.create(groupDto, cluster);

        return ResponseEntity.created(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }

    @LogExecutionTime
    @PutMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}", consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> updateGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @PathVariable final String obfuscatedGroupId,
                                         @RequestBody @Valid final GroupDTO groupDto) {

        groupAdapter.update(groupDto, cluster);

        return ResponseEntity.ok().location(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }

    @LogExecutionTime
    @DeleteMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @PathVariable final String obfuscatedGroupId) {

        groupAdapter.delete(obfuscatedGroupId, cluster);

        return ResponseEntity.ok().location(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }
}
