package org.amhzing.clusterview.web.adapter;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.application.GroupService;
import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.web.model.GroupModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

import static java.util.Collections.emptyList;
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

    private GroupAdapter groupAdapter;

    @Before
    public void setUp() throws Exception {
        groupAdapter = new GroupAdapter(groupService);

        User user = new User("me@example.com", "Nopass" , emptyList());
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void should_get_groups() throws Exception {

        given(groupService.groups(any())).willReturn(ImmutableSet.of(group()));

        final Set<GroupModel> groups = groupAdapter.groups(cluster().getId().getId());

        assertThat(groups).isNotEmpty();

        final GroupModel groupModel = groups.iterator().next();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().coordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().coordY());
    }

    @Test
    public void should_get_group() throws Exception {

        given(groupService.group(any())).willReturn(group());

        final String obfuscatedGroupId = Obfuscator.obfuscate(group().getId().getId());

        final GroupModel groupModel = groupAdapter.group(obfuscatedGroupId);

        assertThat(groupModel).isNotNull();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().coordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().coordY());
    }

    @Test
    public void should_create_group() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(1234L);
        final GroupModel groupModel = groupModel(obfuscatedId);
        final Cluster.Id clusterId = cluster().getId();

        groupAdapter.createGroup(groupModel, clusterId.getId());

        verify(groupService, times(1)).createGroup(eq(convertModel(groupModel)), eq(clusterId));
    }

    @Test
    public void should_update_group() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(1234L);
        final GroupModel groupModel = groupModel(obfuscatedId);
        final Cluster.Id clusterId = cluster().getId();

        groupAdapter.updateGroup(groupModel, clusterId.getId());

        verify(groupService, times(1)).updateGroup(eq(convertModel(groupModel)), eq(clusterId));
    }

    @Test
    public void should_delete_group() throws Exception {

        final Group.Id groupId = group().getId();
        final Cluster.Id clusterId = cluster().getId();

        final String obfuscatedGroupId = Obfuscator.obfuscate(groupId.getId());

        groupAdapter.deleteGroup(obfuscatedGroupId, clusterId.getId());

        verify(groupService, times(1)).deleteGroup(groupId, clusterId);
    }

    private Group convertModel(final GroupModel groupModel) {
        return GroupFactory.convert(groupModel);
    }
}