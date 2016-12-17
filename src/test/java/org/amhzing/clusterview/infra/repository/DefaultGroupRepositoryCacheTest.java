package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.configuration.CacheInvalidateRule;
import org.amhzing.clusterview.configuration.CacheTestConfig;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.amhzing.clusterview.cache.CacheSpec.*;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.teamEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheTestConfig.class)
public class DefaultGroupRepositoryCacheTest {

    private static final String EXISTING_CLUSTER = "ExistingCluster";
    private static final String EXISTING_CLUSTER_2 = "ExistingCluster2";
    private static final String NON_EXISTING_CLUSTER = "ClusterDoesNotExist";

    private static final long EXISTING_GROUP = 123L;
    private static final long EXISTING_GROUP_2 = 456L;

    public static final String CACHE_TEST_KEY = "test";
    public static final String CACHE_TEST_KEY_2 = "test2";
    public static final String CACHE_TEST_VALUE = "value";
    public static final String CACHE_TEST_VALUE_2 = "value2";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ClusterJpaRepository clusterJpaRepository;

    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Rule
    @Autowired
    public CacheInvalidateRule cacheInvalidatenRule;

    @Test
    public void should_invoke_groups_cache() throws Exception {

        given(clusterJpaRepository.findOne(EXISTING_CLUSTER)).willReturn(clusterEntity());

        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER);

        // First call
        final Set<Group> firstCall = groupRepository.groups(clusterId);

        // Second call
        final Set<Group> secondCall = groupRepository.groups(clusterId);

        assertThat(firstCall).isNotEmpty();
        assertThat(secondCall).isNotEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(clusterJpaRepository, times(1)).findOne(clusterId.getId());
    }

    @Test
    public void should_invoke_group_cache() throws Exception {

        given(teamJpaRepository.findOne(EXISTING_GROUP)).willReturn(teamEntity());

        final Group.Id groupId = Group.Id.create(EXISTING_GROUP);

        // First call
        final Group firstCall = groupRepository.group(groupId);

        // Second call
        final Group secondCall = groupRepository.group(groupId);

        assertThat(firstCall).isNotNull();
        assertThat(secondCall).isNotNull();
        assertThat(firstCall).isSameAs(secondCall);

        verify(teamJpaRepository, times(1)).findOne(groupId.getId());
    }

    @Test
    public void should_invoke_groups_cache_when_empty() throws Exception {

        final Cluster.Id clusterId = Cluster.Id.create(NON_EXISTING_CLUSTER);

        // First call
        final Set<Group> firstCall = groupRepository.groups(clusterId);

        // Second call
        final Set<Group> secondCall = groupRepository.groups(clusterId);

        assertThat(firstCall).isEmpty();
        assertThat(secondCall).isEmpty();
        assertThat(firstCall).isSameAs(secondCall);

        verify(clusterJpaRepository, times(1)).findOne(clusterId.getId());
    }

    @Test
    public void should_evict_caches_when_creating_group() throws Exception {

        givenAPopulatedCache();

        whenCreatingGroup();

        // stats cache is cleared
        assertThat(cacheValue(STATS_CACHE_NAME, CACHE_TEST_KEY)).isNull();

        // Only the cluster for the created group in cleared
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey())).isNull();
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);

    }

    @Test
    public void should_evict_caches_when_updating_group() throws Exception {

        givenAPopulatedCache();

        whenUpdatingGroup();

        // stats cache is cleared
        assertThat(cacheValue(STATS_CACHE_NAME, CACHE_TEST_KEY)).isNull();

        // Only the cluster for the updated group in cleared
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey())).isNull();
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);

        // Only the group for the updated group in cleared
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey())).isNull();
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);
    }

    @Test
    public void should_evict_caches_when_deleting_group() throws Exception {

        givenAPopulatedCache();

        whenDeletingGroup();

        // stats cache is cleared
        assertThat(cacheValue(STATS_CACHE_NAME, CACHE_TEST_KEY)).isNull();

        // Only the cluster for the updated group in cleared
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey())).isNull();
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);

        // Only the group for the updated group in cleared
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey())).isNull();
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);
    }

    private void givenAPopulatedCache() {
        given(clusterJpaRepository.findOne(EXISTING_CLUSTER)).willReturn(clusterEntity());
        given(clusterJpaRepository.findOne(EXISTING_CLUSTER_2)).willReturn(clusterEntity());
        given(teamJpaRepository.findOne(EXISTING_GROUP)).willReturn(teamEntity());
        given(teamJpaRepository.findOne(EXISTING_GROUP_2)).willReturn(teamEntity());

        cacheManager.getCache(STATS_CACHE_NAME).put(CACHE_TEST_KEY, CACHE_TEST_VALUE);
        cacheManager.getCache(STATS_CACHE_NAME).put(CACHE_TEST_KEY_2, CACHE_TEST_VALUE_2);
        cacheManager.getCache(GROUPS_CACHE_NAME).put(groupsCacheKey(), CACHE_TEST_VALUE);
        cacheManager.getCache(GROUPS_CACHE_NAME).put(groupsCacheKey2(), CACHE_TEST_VALUE_2);
        cacheManager.getCache(GROUP_CACHE_NAME).put(groupCacheKey(), CACHE_TEST_VALUE);
        cacheManager.getCache(GROUP_CACHE_NAME).put(groupCacheKey2(), CACHE_TEST_VALUE_2);

        assertThat(cacheValue(STATS_CACHE_NAME, CACHE_TEST_KEY)).isEqualTo(CACHE_TEST_VALUE);
        assertThat(cacheValue(STATS_CACHE_NAME, CACHE_TEST_KEY_2)).isEqualTo(CACHE_TEST_VALUE_2);
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey())).isEqualTo(CACHE_TEST_VALUE);
        assertThat(cacheValue(GROUPS_CACHE_NAME, groupsCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey())).isEqualTo(CACHE_TEST_VALUE);
        assertThat(cacheValue(GROUP_CACHE_NAME, groupCacheKey2())).isEqualTo(CACHE_TEST_VALUE_2);
    }

    private void whenCreatingGroup() {
        groupRepository.createGroup(group(), clusterId());
    }

    private void whenUpdatingGroup() {
        groupRepository.updateGroup(group(), clusterId());
    }

    private void whenDeletingGroup() {
        groupRepository.deleteGroup(group().getId(), clusterId());
    }

    private String groupCacheKey2() {
        final Group.Id groupId = Group.Id.create(EXISTING_GROUP_2);
        return GROUP_CACHE_NAME + '_' + groupId;
    }

    private String groupCacheKey() {
        return GROUP_CACHE_NAME + '_' + group().getId();
    }

    private String groupsCacheKey2() {
        final Cluster.Id clusterId = Cluster.Id.create(EXISTING_CLUSTER_2);
        return GROUPS_CACHE_NAME + '_' + clusterId;
    }

    private String groupsCacheKey() {
        return GROUPS_CACHE_NAME + '_' + clusterId();
    }

    private Cluster.Id clusterId() {
        return Cluster.Id.create(EXISTING_CLUSTER);
    }

    private String cacheValue(final String name, final String key) {
        final Cache.ValueWrapper valueWrapper = cacheManager.getCache(name).get(key);

        if (valueWrapper == null) {
            return null;
        }

        return (String) valueWrapper.get();
    }
}