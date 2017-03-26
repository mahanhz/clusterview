package org.amhzing.clusterview.app.web.controller.rest.appnav;

import org.amhzing.clusterview.app.annotation.LogExecutionTime;
import org.amhzing.clusterview.app.api.GroupDTO;
import org.amhzing.clusterview.app.api.GroupsDTO;
import org.amhzing.clusterview.app.application.GroupService;
import org.amhzing.clusterview.app.domain.model.Cluster;
import org.amhzing.clusterview.app.domain.model.Group;
import org.amhzing.clusterview.app.web.adapter.Obfuscator;
import org.amhzing.clusterview.app.web.controller.rest.base.AbstractRestController;
import org.amhzing.clusterview.app.web.controller.rest.util.GroupDtoFactory;
import org.amhzing.clusterview.app.web.model.ClusterPath;
import org.amhzing.clusterview.app.web.model.GroupPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.amhzing.clusterview.app.web.controller.rest.appnav.CommonLinks.*;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class GroupRestController extends AbstractRestController {

    public static final String CLUSTER_PATH = "/{country}/{region}/{cluster}";

    private GroupService groupService;

    @Autowired
    public GroupRestController(final GroupService groupService) {
        this.groupService = notNull(groupService);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH)
    public ResponseEntity<GroupsDTO> groups(final ClusterPath clusterPath) {

        final String country = clusterPath.getCountry();
        final String region = clusterPath.getRegion();
        final String cluster = clusterPath.getCluster();

        final Set<Group> groups = groupService.groups(Cluster.Id.create(cluster));

        final GroupsDTO groupsDto = GroupDtoFactory.convertGroups(groups);

        final ControllerLinkBuilder selfLink = linkTo(GroupRestController.class).slash(country)
                                                                                .slash(region)
                                                                                .slash(cluster);

        groupsDto.add(selfLink.withSelfRel());
        groupsDto.add(homeLink());
        groupsDto.add(countryLink(country));
        groupsDto.add(regionLink(country, region));
        groupsDto.add(clusterLink(country, region, cluster));
        groupsDto.add(clusterActivityStatsLink(country, region, cluster));
        groupsDto.add(clusterCourseStatsLink(country, region, cluster));
        groupsDto.add(groupLinks(country, region, cluster));
        groupsDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(groupsDto);
    }

    @LogExecutionTime
    @GetMapping(path = CLUSTER_PATH + "/{obfuscatedGroupId}")
    public ResponseEntity<GroupDTO> group(final GroupPath groupPath) {

        final String country = groupPath.getCountry();
        final String region = groupPath.getRegion();
        final String cluster = groupPath.getCluster();
        final String obfuscatedGroupId = groupPath.getObfuscatedGroupId();

        final long groupId = Obfuscator.deobfuscate(obfuscatedGroupId);
        final Group group = groupService.group(Group.Id.create(groupId));

        final GroupDTO groupDto = GroupDtoFactory.convertGroup(group);

        final ControllerLinkBuilder selfLink = linkTo(GroupRestController.class).slash(country)
                                                                                .slash(region)
                                                                                .slash(cluster)
                                                                                .slash(obfuscatedGroupId);

        groupDto.add(selfLink.withSelfRel());
        groupDto.add(homeLink());
        groupDto.add(countryLink(country));
        groupDto.add(regionLink(country, region));
        groupDto.add(clusterLink(country, region, cluster));
        groupDto.add(statsHistoryLink(country));

        return ResponseEntity.ok(groupDto);
    }

    private List<Link> groupLinks(final String country, final String region, final String cluster) {
        final Set<Group> groups = groupService.groups(Cluster.Id.create(cluster));
        final GroupsDTO groupsDto = GroupDtoFactory.convertGroups(groups);

        final List<Link> groupLinks = groupsDto.groups.stream()
                                                      .map(group -> linkTo(GroupRestController.class).slash(country)
                                                                                                     .slash(region)
                                                                                                     .slash(cluster)
                                                                                                     .slash(group.id)
                                                                                                     .withRel(GROUP_PREFIX + group.id))
                                                      .collect(Collectors.toList());

        return groupLinks;
    }
}
