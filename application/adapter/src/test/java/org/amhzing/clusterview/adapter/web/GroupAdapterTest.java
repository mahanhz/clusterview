package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.adapter.web.api.GroupDTO;
import org.amhzing.clusterview.adapter.web.api.GroupsDTO;
import org.amhzing.clusterview.core.boundary.enter.GroupService;
import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.core.helper.DomainModelHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.adapter.web.util.GroupDtoFactory.convertGroup;
import static org.amhzing.clusterview.adapter.web.util.GroupFactory.convert;
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
    }

    @Test
    public void groups() throws Exception {

        given(groupService.groups(any())).willReturn(ImmutableSet.of(DomainModelHelper.group()));

        final GroupsDTO groups = groupAdapter.groups("clusterId1");

        assertThat(groups.groups).isNotEmpty();

        final GroupDTO groupDTO = groups.groups.get(0);
        final long deobfuscatedId = Obfuscator.deobfuscate(groupDTO.id);
        assertThat(deobfuscatedId).isEqualTo(DomainModelHelper.group().getId().getId());
    }

    @Test
    public void group() throws Exception {

        given(groupService.group(any())).willReturn(DomainModelHelper.group());

        final String obfuscatedGroupId = Obfuscator.obfuscate(DomainModelHelper.group().getId().getId());
        final GroupDTO group = groupAdapter.group(obfuscatedGroupId);

        final long deobfuscatedId = Obfuscator.deobfuscate(group.id);
        assertThat(deobfuscatedId).isEqualTo(DomainModelHelper.group().getId().getId());
    }

    @Test
    public void create() throws Exception {
        final GroupDTO groupDTO = convertGroup(DomainModelHelper.group());
        final Cluster.Id cluster = Cluster.Id.create("cluster1");

        groupAdapter.create(groupDTO, cluster.getId());

        verify(groupService, times(1)).createGroup(eq(convert(groupDTO)), eq(cluster));
    }

    @Test
    public void update() throws Exception {
        final GroupDTO groupDTO = convertGroup(DomainModelHelper.group());
        final Cluster.Id cluster = Cluster.Id.create("cluster1");

        groupAdapter.update(groupDTO, cluster.getId());

        verify(groupService, times(1)).updateGroup(eq(convert(groupDTO)), eq(cluster));
    }

    @Test
    public void delete() throws Exception {
        final Group.Id groupId = DomainModelHelper.group().getId();
        final Cluster.Id clusterId = DomainModelHelper.cluster().getId();

        final String obfuscatedGroupId = Obfuscator.obfuscate(groupId.getId());

        groupAdapter.delete(obfuscatedGroupId, clusterId.getId());

        verify(groupService, times(1)).deleteGroup(groupId, clusterId);
    }

}