package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.mapping.ClusterEntity;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.amhzing.clusterview.cache.CacheSpec.*;
import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeam;
import static org.amhzing.clusterview.infra.repository.GroupFactory.convertTeams;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupRepository implements GroupRepository {

    private ClusterJpaRepository clusterJpaRepository;
    private TeamJpaRepository teamJpaRepository;
    private ActivityJpaRepository activityJpaRepository;

    public DefaultGroupRepository(final ClusterJpaRepository clusterJpaRepository,
                                  final TeamJpaRepository teamJpaRepository,
                                  final ActivityJpaRepository activityJpaRepository) {
        this.clusterJpaRepository = notNull(clusterJpaRepository);
        this.teamJpaRepository = notNull(teamJpaRepository);
        this.activityJpaRepository = notNull(activityJpaRepository);
    }

    @Override
    @Cacheable(cacheNames = GROUPS_CACHE_NAME,
               key= DEFAULT_CACHE_KEY,
               unless = "#result == null")
    public Set<Group> groups(final Cluster.Id clusterId) {
        notNull(clusterId);

        final ClusterEntity cluster = clusterJpaRepository.findOne(clusterId.getId());

        if (cluster != null) {
            return convertTeams(cluster.getTeams());
        }

        return emptySet();
    }

    @Override
    @Cacheable(cacheNames = GROUP_CACHE_NAME,
               key= DEFAULT_CACHE_KEY,
               unless = "#result == null")
    public Group group(final Group.Id groupId) {
        notNull(groupId);

        final TeamEntity team = teamJpaRepository.findOne(groupId.getId());

        return convertTeam(team);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1") })
    public TeamEntity createGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final ClusterEntity clusterEntity = clusterJpaRepository.findOne(clusterId.getId());

        final TeamEntityFactory teamEntityFactory = TeamEntityFactory.create(activityJpaRepository);
        final TeamEntity teamEntity = teamEntityFactory.convertGroupForNewTeam(group, clusterEntity);

        return teamJpaRepository.save(teamEntity);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1"),
                       @CacheEvict(cacheNames = GROUP_CACHE_NAME, key = "#root.caches[0].name + '_' + #p0.id")})
    public TeamEntity updateGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final TeamEntity currentTeam = teamJpaRepository.findOne(group.getId().getId());

        final TeamEntityFactory teamEntityFactory = TeamEntityFactory.create(activityJpaRepository);
        final TeamEntity updatedTeam = teamEntityFactory.convertGroupForExistingTeam(group, currentTeam);

        return teamJpaRepository.save(updatedTeam);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1"),
                       @CacheEvict(cacheNames = GROUP_CACHE_NAME, key = DEFAULT_CACHE_KEY)})
    public void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId) {
        notNull(groupId);
        notNull(clusterId);

        teamJpaRepository.delete(groupId.getId());
    }
}
