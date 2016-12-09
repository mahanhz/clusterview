package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.cache.CacheSpec;
import org.amhzing.clusterview.configuration.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultGroupRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";
    private static final String NON_EXISTING_CLUSTER = "ClusterDoesNotExist";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Cache groupsCache;

    @Before
    public void setUp() {
        groupsCache = cacheManager.getCache(CacheSpec.GROUPS_CACHE_NAME);
    }

    @Test
    public void should_invoke_cache() throws Exception {

        given(clusterJpaRepository.findOne(EXISTING_CLUSTER)).willReturn(clusterEntity());

        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER);

        // First call
        final Set<Group> firstCall = groupRepository.groups(clusterId);

        // Second call
        final Set<Group> secondCall = groupRepository.groups(clusterId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        assertThat(groupsCache).isNotNull();

        verify(clusterJpaRepository, times(1)).findOne(clusterId.getId());
    }

    @Test
    public void should_not_invoke_cache_when_empty() throws Exception {

        final Cluster.Id clusterId = Cluster.Id.create(NON_EXISTING_CLUSTER);

        // First call
        final Set<Group> firstCall = groupRepository.groups(clusterId);

        // Second call
        final Set<Group> secondCall = groupRepository.groups(clusterId);

        verify(clusterJpaRepository, times(2)).findOne(clusterId.getId());
    }
}