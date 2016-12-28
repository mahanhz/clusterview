package org.amhzing.clusterview.acceptancetest.helper;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.infra.jpa.mapping.*;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class GroupHelper {

    private GroupHelper() {
        // Prevent instantiation
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
