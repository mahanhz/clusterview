package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.data.jpa.entity.ClusterEntity;
import org.amhzing.clusterview.data.jpa.entity.TeamEntity;
import org.amhzing.clusterview.data.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.TeamJpaRepository;
import org.amhzing.clusterview.data.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

import static java.util.Collections.emptySet;
import static org.amhzing.clusterview.data.repository.GroupFactory.convertTeam;
import static org.amhzing.clusterview.data.repository.GroupFactory.convertTeams;
import static org.amhzing.clusterview.data.cache.CacheSpec.*;
import static org.apache.commons.lang3.Validate.notNull;

public class DefaultGroupRepository implements GroupRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGroupRepository.class);

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

        if (cluster == null) {
            return emptySet();
        }

        return convertTeams(cluster.getTeams());
    }

    @Override
    @Cacheable(cacheNames = GROUP_CACHE_NAME,
               key= DEFAULT_CACHE_KEY,
               unless = "#result == null")
    public Group group(final Group.Id groupId) {
        notNull(groupId);

        final TeamEntity team = teamJpaRepository.findOne(groupId.getId());

        if (team == null) {
            throw new NotFoundException("Group '" + groupId + "' does not exist");
        }

        return convertTeam(team);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1") })
    public Group createGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final String id = clusterId.getId();
        final ClusterEntity clusterEntity = clusterJpaRepository.findOne(id);

        if (clusterEntity == null) {
            throw new NotFoundException("Cluster '" + id + "' does not exist");
        }

        final TeamEntityFactory teamEntityFactory = TeamEntityFactory.create(activityJpaRepository);
        final TeamEntity teamEntity = teamEntityFactory.convertGroupForNewTeam(group, clusterEntity);

        return convertTeam(teamJpaRepository.save(teamEntity));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1"),
                       @CacheEvict(cacheNames = GROUP_CACHE_NAME, key = "#root.caches[0].name + '_' + #p0.id")})
    public Group updateGroup(final Group group, final Cluster.Id clusterId) {
        notNull(group);
        notNull(clusterId);

        final long id = group.getId().getId();
        final TeamEntity currentTeam = teamJpaRepository.findOne(id);

        if (currentTeam == null) {
            throw new NotFoundException("Group '" + id + "' does not exist");
        }

        final TeamEntityFactory teamEntityFactory = TeamEntityFactory.create(activityJpaRepository);
        final TeamEntity updatedTeam = teamEntityFactory.convertGroupForExistingTeam(group, currentTeam);

        return convertTeam(teamJpaRepository.save(updatedTeam));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') and @webSecurity.checkAdmin(authentication, #clusterId.id)")
    @Caching(evict = { @CacheEvict(cacheNames = STATS_CACHE_NAME, allEntries = true),
                       @CacheEvict(cacheNames = GROUPS_CACHE_NAME, key = "#root.caches[0].name + '_' + #p1"),
                       @CacheEvict(cacheNames = GROUP_CACHE_NAME, key = DEFAULT_CACHE_KEY)})
    public void deleteGroup(final Group.Id groupId, final Cluster.Id clusterId) {
        notNull(groupId);
        notNull(clusterId);

        teamJpaRepository.delete(groupId.getId());
    }
}
