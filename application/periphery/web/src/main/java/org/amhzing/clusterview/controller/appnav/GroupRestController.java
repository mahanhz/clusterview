package org.amhzing.clusterview.controller.appnav;

import org.amhzing.clusterview.Obfuscator;
import org.amhzing.clusterview.api.GroupDTO;
import org.amhzing.clusterview.api.GroupsDTO;
import org.amhzing.clusterview.boundary.enter.GroupService;
import org.amhzing.clusterview.controller.base.AbstractRestController;
import org.amhzing.clusterview.controller.util.GroupDtoFactory;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.controller.appnav.GroupEditRestController.CREATE_GROUP;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GroupRestController extends AbstractRestController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";

    private GroupService groupService;

    @Autowired
    public GroupRestController(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    @GetMapping(path = CLUSTER_PATH)
    public ResponseEntity<GroupsDTO> groups(@PathVariable final String country,
                                            @PathVariable final String region,
                                            @PathVariable final String cluster) {

        final Set<Group> groups = groupService.groups(Cluster.Id.create(cluster));

        final GroupsDTO groupsDto = GroupDtoFactory.convertGroups(groups);

        final ControllerLinkBuilder selfLink = linkTo(methodOn(GroupRestController.class).groups(country, region, cluster));

        final ControllerLinkBuilder createGroupLink = linkTo(GroupEditRestController.class).slash(country)
                                                                                           .slash(region)
                                                                                           .slash(cluster)
                                                                                           .slash(CREATE_GROUP);

        groupsDto.add(selfLink.withSelfRel());
        groupsDto.add(homeLink());
        groupsDto.add(countryLink(country));
        groupsDto.add(regionLink(country, region));
        groupsDto.add(clusterLink(country, region, cluster));
        groupsDto.add(clusterActivityStatsLink(country, region, cluster));
        groupsDto.add(clusterCourseStatsLink(country, region, cluster));
        groupsDto.add(groupLinks(country, region, cluster));
        groupsDto.add(createGroupLink.withRel(GROUP_PREFIX + "create"));
        groupsDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(groupsDto);
    }

    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ResponseEntity<GroupDTO> group(@PathVariable final String country,
                                          @PathVariable final String region,
                                          @PathVariable final String cluster,
                                          @PathVariable final String obfuscatedGroupId) {

        final long groupId = Obfuscator.deobfuscate(obfuscatedGroupId);
        final Group group = groupService.group(Group.Id.create(groupId));

        final GroupDTO groupDto = GroupDtoFactory.convertGroup(group);

        final ControllerLinkBuilder selfLink = linkTo(methodOn(GroupRestController.class).group(country, region, cluster, obfuscatedGroupId));

        final ControllerLinkBuilder actionGroupLink = linkTo(GroupEditRestController.class).slash(country)
                                                                                           .slash(region)
                                                                                           .slash(cluster)
                                                                                           .slash(obfuscatedGroupId);

        groupDto.add(selfLink.withSelfRel());
        groupDto.add(homeLink());
        groupDto.add(countryLink(country));
        groupDto.add(regionLink(country, region));
        groupDto.add(clusterLink(country, region, cluster));
        groupDto.add(actionGroupLink.withRel(GROUP_PREFIX + "update"));
        groupDto.add(actionGroupLink.withRel(GROUP_PREFIX + "delete"));
        groupDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(groupDto);
    }

    private List<Link> groupLinks(final String country, final String region, final String cluster) {
        final Set<Group> groups = groupService.groups(Cluster.Id.create(cluster));
        final GroupsDTO groupsDto = GroupDtoFactory.convertGroups(groups);

        final List<Link> groupLinks = groupsDto.groups.stream()
                                                      .map(group -> linkTo(methodOn(GroupRestController.class).group(country, region, cluster, group.id)).withRel(GROUP_PREFIX + group.id))
                                                      .collect(Collectors.toList());

        return groupLinks;
    }
}
