package org.amhzing.clusterview.web.adapter;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.application.GroupService;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.web.model.GroupModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.amhzing.clusterview.helper.ClientModelHelper.groupModel;
import static org.amhzing.clusterview.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GroupAdapterTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupAdapter groupAdapter;

    @Test
    public void should_get_groups() throws Exception {

        given(groupService.groups(any())).willReturn(ImmutableSet.of(group()));

        final Set<GroupModel> groups = groupAdapter.groups(cluster().getId().getId());

        assertThat(groups).isNotEmpty();

        final GroupModel groupModel = groups.iterator().next();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().getCoordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().getCoordY());
    }

    @Test
    public void should_get_group() throws Exception {

        given(groupService.group(any())).willReturn(group());

        final GroupModel groupModel = groupAdapter.group(group().getId().getId());

        assertThat(groupModel).isNotNull();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().getCoordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().getCoordY());
    }

    @Test
    public void should_create_group() throws Exception {

        final GroupModel groupModel = groupModel();
        final Cluster.Id clusterId = cluster().getId();

        groupAdapter.createGroup(groupModel, clusterId.getId());

        verify(groupService, times(1)).createGroup(eq(convertModel(groupModel)), eq(clusterId));
    }

    @Test
    public void should_update_group() throws Exception {

        final GroupModel groupModel = groupModel();
        final Cluster.Id clusterId = cluster().getId();

        groupAdapter.updateGroup(groupModel, clusterId.getId());

        verify(groupService, times(1)).updateGroup(eq(convertModel(groupModel)), eq(clusterId));
    }

    @Test
    public void should_delete_group() throws Exception {

        final Group.Id groupId = group().getId();
        final Cluster.Id clusterId = cluster().getId();

        groupAdapter.deleteGroup(groupId.getId(), clusterId.getId());

        verify(groupService, times(1)).deleteGroup(groupId, clusterId);
    }

    private Group convertModel(final GroupModel groupModel) {
        return GroupFactory.convert(groupModel);
    }
}