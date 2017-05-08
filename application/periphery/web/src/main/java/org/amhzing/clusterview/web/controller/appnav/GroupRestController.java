package org.amhzing.clusterview.web.controller.appnav;

import org.amhzing.clusterview.adapter.web.GroupAdapter;
import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.adapter.web.api.GroupsDTO;
import org.amhzing.clusterview.web.controller.base.AbstractRestController;
import org.amhzing.clusterview.web.timing.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.web.controller.appnav.CommonLinks.*;
import static org.amhzing.clusterview.web.controller.appnav.GroupEditRestController.CREATE_GROUP;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GroupRestController extends AbstractRestController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";

    private GroupAdapter groupAdapter;

    @Autowired
    public GroupRestController(final GroupAdapter groupAdapter) {
        this.groupAdapter = notNull(groupAdapter);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH)
    public ResponseEntity<Resource<GroupsDTO>> groups(@PathVariable final String country,
                                            @PathVariable final String region,
                                            @PathVariable final String cluster) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(GroupRestController.class).groups(country, region, cluster));

        final ControllerLinkBuilder createGroupLink = linkTo(GroupEditRestController.class).slash(country)
                                                                                           .slash(region)
                                                                                           .slash(cluster)
                                                                                           .slash(CREATE_GROUP);

        final Resource<GroupsDTO> groupsDto = new Resource<>(groupAdapter.groups(cluster));
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

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ResponseEntity<Resource<GroupDTO>> group(@PathVariable final String country,
                                          @PathVariable final String region,
                                          @PathVariable final String cluster,
                                          @PathVariable final String obfuscatedGroupId) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(GroupRestController.class).group(country, region, cluster, obfuscatedGroupId));

        final ControllerLinkBuilder actionGroupLink = linkTo(GroupEditRestController.class).slash(country)
                                                                                           .slash(region)
                                                                                           .slash(cluster)
                                                                                           .slash(obfuscatedGroupId);

        final Resource<GroupDTO> groupDto = new Resource<>(groupAdapter.group(obfuscatedGroupId));
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
        final GroupsDTO groupsDto = groupAdapter.groups(cluster);

        return groupsDto.groups.stream()
                               .map(group -> linkTo(linkToValue(country, region, cluster, group)).withRel(rel(group)))
                               .collect(Collectors.toList());
    }

    private String rel(final GroupDTO group) {
        return GROUP_PREFIX + group.id;
    }

    private ResponseEntity<Resource<GroupDTO>> linkToValue(final String country, final String region, final String cluster, final GroupDTO group) {
        return methodOn(GroupRestController.class).group(country, region, cluster, group.id);
    }
}
