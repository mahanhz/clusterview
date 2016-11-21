package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.application.ClusterService;
import org.amhzing.clusterview.domain.model.*;
import org.amhzing.clusterview.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ClusterAdapter {

    private ClusterService clusterService;

    @Autowired
    public ClusterAdapter(final ClusterService clusterService) {
        this.clusterService = notNull(clusterService);
    }

    public Set<GroupModel> groups(final String cluster) {
        final Set<Group> groups = clusterService.groups(cluster);

        return convertGroups(groups);
    }

    private Set<GroupModel> convertGroups(final Set<Group> groups) {
        return groups.stream()
                     .map(this::createGroup)
                     .collect(Collectors.toSet());
    }

    private GroupModel createGroup(final Group group) {
        return GroupModel.create(group.getId().getId(),
                                 convertMembers(group.getMembers()),
                                 convertLocation(group.getLocation()));
    }

    private LocationModel convertLocation(final Location location) {
        return LocationModel.create(location.getCoordX(), location.getCoordY());
    }

    private Set<MemberModel> convertMembers(final Set<Member> members) {
        return members.stream()
                      .map(this::createMember)
                      .collect(Collectors.toSet());
    }

    private MemberModel createMember(final Member member) {
        return MemberModel.create(member.getId().getId(),
                                  convertName(member.getName()),
                                  convertCapabilities(member.getCapability()),
                                  convertCommitments(member.getCommitment()));
    }

    private NameModel convertName(final Name name) {
        return NameModel.create(name.getFirstName().getValue(),
                                name.getMiddleName().getValue(),
                                name.getLastName().getValue(),
                                name.getSuffix().getValue());
    }

    private CapabilityModel convertCapabilities(final Capability capability) {
        return CapabilityModel.create(activities(capability.getActivities()));
    }

    private CommitmentModel convertCommitments(final Commitment commitment) {
        return CommitmentModel.create(activities(commitment.getActivities()));
    }

    private Set<ActivityModel> activities(final Set<Activity> activities) {
        return activities.stream()
                         .map(this::activity)
                         .collect(Collectors.toSet());
    }

    private ActivityModel activity(final Activity activity) {
        return ActivityModel.create(activity.getId().getId(), activity.getName());
    }
}
