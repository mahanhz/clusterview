package org.amhzing.clusterview.application;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.domain.repository.GroupRepository;
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
        assertThat(group.getLocation().getCoordX()).isEqualTo(clusterGroup.getLocation().getCoordX());
        assertThat(group.getLocation().getCoordY()).isEqualTo(clusterGroup.getLocation().getCoordY());
    }

    @Test
    public void should_get_group() throws Exception {

        given(groupRepository.group(any())).willReturn(group());

        final Group group = defaultGroupService.group(group().getId());

        assertThat(group).isNotNull();
        assertThat(group.getLocation().getCoordX()).isEqualTo(group().getLocation().getCoordX());
        assertThat(group.getLocation().getCoordY()).isEqualTo(group().getLocation().getCoordY());
    }

    @Test
    public void should_update_group() throws Exception {

        final Group group = group();
        defaultGroupService.updateGroup(group);

        verify(groupRepository, times(1)).updateGroup(group);
    }

    @Test
    public void should_delete_group() throws Exception {

        defaultGroupService.deleteGroup(group().getId());

        verify(groupRepository, times(1)).deleteGroup(group().getId());
    }
}