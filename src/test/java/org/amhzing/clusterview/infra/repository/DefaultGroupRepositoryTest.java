package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.domain.model.Cluster;
import org.amhzing.clusterview.domain.model.Group;
import org.amhzing.clusterview.exception.NotFoundException;
import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.infra.jpa.repository.TeamJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.amhzing.clusterview.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.teamEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultGroupRepositoryTest {

    @Mock
    private ClusterJpaRepository clusterJpaRepository;
    @Mock
    private TeamJpaRepository teamJpaRepository;
    @Mock
    private ActivityJpaRepository activityJpaRepository;

    @InjectMocks
    private DefaultGroupRepository defaultGroupRepository;

    @Test
    public void should_get_groups() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

        final Set<Group> groups = defaultGroupRepository.groups(Cluster.Id.create(clusterEntity().getId()));

        assertThat(groups).isNotEmpty();

        final Group group = groups.iterator().next();
        final TeamEntity teamEntity = clusterEntity().getTeams().iterator().next();
        assertThat(group.getLocation().coordX()).isEqualTo(teamEntity.getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(teamEntity.getLocation().getY());
    }

    @Test
    public void should_get_empty_groups() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(null);

        final Set<Group> groups = defaultGroupRepository.groups(Cluster.Id.create("fake"));

        assertThat(groups).isEmpty();
    }

    @Test
    public void should_get_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(teamEntity());

        final Group group = defaultGroupRepository.group(Group.Id.create(teamEntity().getId()));

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(teamEntity().getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(teamEntity().getLocation().getY());
    }

    @Test
    public void should_get_empty_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(null);

        final Group.Id id = Group.Id.create(1234L);
        final Group group = defaultGroupRepository.group(id);

        assertThat(group).isNotNull();
        assertThat(group).isEqualTo(Group.empty(id));
    }

    @Test
    public void should_create_group() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(clusterEntity());

        given(teamJpaRepository.save(any(TeamEntity.class))).willReturn(teamEntity());

        final TeamEntity teamEntity = defaultGroupRepository.createGroup(group(), cluster().getId());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity.getLocation().getX()).isEqualTo(teamEntity().getLocation().getX());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(teamEntity().getLocation().getX());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_create_group() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(null);

        defaultGroupRepository.createGroup(group(), cluster().getId());

        fail("How did we get this far?");
    }

    @Test
    public void should_update_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(teamEntity());
        given(teamJpaRepository.save(any(TeamEntity.class))).willReturn(teamEntity());

        final TeamEntity teamEntity = defaultGroupRepository.updateGroup(group(), cluster().getId());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity.getLocation().getX()).isEqualTo(teamEntity().getLocation().getX());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(teamEntity().getLocation().getX());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_update_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(null);

        defaultGroupRepository.updateGroup(group(), cluster().getId());

        fail("How did we get this far?");
    }

    @Test
    public void should_delete_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(teamEntity());

        defaultGroupRepository.deleteGroup(group().getId(), cluster().getId());

        verify(teamJpaRepository, times(1)).delete(group().getId().getId());
    }
}