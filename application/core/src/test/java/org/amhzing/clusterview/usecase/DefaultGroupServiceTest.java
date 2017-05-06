package org.amhzing.clusterview.usecase;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.boundary.exit.repository.GroupRepository;
import org.amhzing.clusterview.domain.Cluster;
import org.amhzing.clusterview.domain.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.amhzing.clusterview.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultGroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private DefaultGroupService defaultGroupService;

    @Test
    public void should_get_groups() throws Exception {

        given(groupRepository.groups(any())).willReturn(ImmutableSet.of(group()));

        final Set<Group> groups = defaultGroupService.groups(cluster().getId());

        assertThat(groups).isNotEmpty();

        final Group group = groups.iterator().next();
        final Group clusterGroup = cluster().getGroups().iterator().next();
        assertThat(group.getLocation().coordX()).isEqualTo(clusterGroup.getLocation().coordX());
        assertThat(group.getLocation().coordY()).isEqualTo(clusterGroup.getLocation().coordY());
    }

    @Test
    public void should_get_group() throws Exception {

        given(groupRepository.group(any())).willReturn(group());

        final Group group = defaultGroupService.group(group().getId());

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(group().getLocation().coordX());
        assertThat(group.getLocation().coordY()).isEqualTo(group().getLocation().coordY());
    }

    @Test
    public void should_update_group() throws Exception {

        final Group group = group();
        final Cluster.Id clusterId = cluster().getId();

        defaultGroupService.updateGroup(group, clusterId);

        verify(groupRepository, times(1)).updateGroup(group, clusterId);
    }

    @Test
    public void should_delete_group() throws Exception {

        final Group.Id groupId = group().getId();
        final Cluster.Id clusterId = cluster().getId();

        defaultGroupService.deleteGroup(groupId, clusterId);

        verify(groupRepository, times(1)).deleteGroup(groupId, clusterId);
    }
}