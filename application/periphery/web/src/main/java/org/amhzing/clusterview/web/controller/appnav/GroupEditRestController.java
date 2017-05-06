package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.core.boundary.enter.GroupService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.web.Obfuscator;
import org.amhzing.clusterview.web.api.GroupDTO;
import org.amhzing.clusterview.web.controller.base.AbstractEditRestController;
import org.amhzing.clusterview.web.controller.util.GroupFactory;
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

    private GroupService groupService;

    @Autowired
    public GroupEditRestController(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    @PostMapping(path = GroupRestController.CLUSTER_PATH + "/" + CREATE_GROUP, consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> createGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @RequestBody @Valid final GroupDTO groupDto) {

        final Group group = GroupFactory.convert(groupDto);
        groupService.createGroup(group, Cluster.Id.create(cluster));

        return ResponseEntity.created(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }

    @PutMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}", consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> updateGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @PathVariable final String obfuscatedGroupId,
                                         @RequestBody @Valid final GroupDTO groupDto) {

        final Group group = GroupFactory.convert(groupDto);
        groupService.updateGroup(group, Cluster.Id.create(cluster));

        return ResponseEntity.ok().location(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }

    @DeleteMapping(path = GroupRestController.CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable final String country,
                                         @PathVariable final String region,
                                         @PathVariable final String cluster,
                                         @PathVariable final String obfuscatedGroupId) {

        final long groupId = Obfuscator.deobfuscate(obfuscatedGroupId);
        groupService.deleteGroup(Group.Id.create(groupId), Cluster.Id.create(cluster));

        return ResponseEntity.ok().location(URI.create(clusterLink(country, region, cluster).getHref())).build();
    }
}
