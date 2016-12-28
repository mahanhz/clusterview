package org.amhzing.clusterview.acceptancetest.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupHelper {

    private GroupHelper() {
        // Prevent instantiation
    }

    public static MultiValueMap<String, String> createGroupForm() {
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.set("id", "0");
        form.set("location.coordX", "100");
        form.set("location.coordY", "100");
        form.set("coreActivities[0].id", "cc");
        form.set("coreActivities[0].name", "CC");
        form.set("coreActivities[0].quantity", "0");
        form.set("coreActivities[0].totalParticipants", "0");
        form.set("coreActivities[0].communityOfInterest", "0");
        form.set("coreActivities[1].id", "dm");
        form.set("coreActivities[1].name", "DM");
        form.set("coreActivities[1].quantity", "0");
        form.set("coreActivities[1].totalParticipants", "0");
        form.set("coreActivities[1].communityOfInterest", "0");
        form.set("coreActivities[2].id", "jyg");
        form.set("coreActivities[2].name", "JYG");
        form.set("coreActivities[2].quantity", "0");
        form.set("coreActivities[2].totalParticipants", "0");
        form.set("coreActivities[2].communityOfInterest", "0");
        form.set("coreActivities[3].id", "sc");
        form.set("coreActivities[3].name", "SC");
        form.set("coreActivities[3].quantity", "0");
        form.set("coreActivities[3].totalParticipants", "0");
        form.set("coreActivities[3].communityOfInterest", "0");
        form.set("members[0].name.firstName", "testF");
        form.set("members[0].name.lastName", "testL");

        return  form;
    }

    public static TeamEntity teamEntity(final ClusterJpaRepository clusterJpaRepository, final String clusterId) {
        notNull(clusterJpaRepository);
        notBlank(clusterId);

        final TeamEntity teamEntity = new TeamEntity();
        teamEntity.setLocation(location());
        teamEntity.setMembers(ImmutableSet.of(member(teamEntity)));
        teamEntity.setCoreActivities(emptyMap());
        teamEntity.setCluster(clusterEntity(clusterJpaRepository, clusterId));

        return teamEntity;
    }

    private static MemberEntity member(final TeamEntity teamEntity) {
        final MemberEntity member = new MemberEntity();
        member.setName(name());
        member.setCapabilities(emptySet());
        member.setCommitments(emptySet());
        member.setTeam(teamEntity);

        return member;
    }

    private static Location location() {
        return Location.create(135, 75);
    }

    private static ClusterEntity clusterEntity(final ClusterJpaRepository clusterJpaRepository, final String clusterId) {
        return clusterJpaRepository.findOne(clusterId);
    }

    private static Name name() {
        return Name.create("John", null, "Accept", null);
    }
}
