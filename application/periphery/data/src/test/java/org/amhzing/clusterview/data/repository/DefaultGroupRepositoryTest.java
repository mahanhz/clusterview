package org.amhzing.clusterview.data.repository;

import org.amhzing.clusterview.core.domain.Cluster;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.data.exception.NotFoundException;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.data.jpa.entity.TeamEntity;
import org.amhzing.clusterview.data.jpa.repository.ActivityJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.ClusterJpaRepository;
import org.amhzing.clusterview.data.jpa.repository.TeamJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.amhzing.clusterview.data.helper.DomainModelHelper.cluster;
import static org.amhzing.clusterview.data.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.clusterEntity());

        final Set<Group> groups = defaultGroupRepository.groups(Cluster.Id.create(JpaRepositoryHelper.clusterEntity().getId()));

        assertThat(groups).isNotEmpty();

        final Group group = groups.iterator().next();
        final TeamEntity teamEntity = JpaRepositoryHelper.clusterEntity().getTeams().iterator().next();
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

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(JpaRepositoryHelper.teamEntity());

        final Group group = defaultGroupRepository.group(Group.Id.create(JpaRepositoryHelper.teamEntity().getId()));

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getY());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_get_empty_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(null);

        final Group.Id id = Group.Id.create(1234L);
        final Group group = defaultGroupRepository.group(id);

        fail("How did we get this far?");
    }

    @Test
    public void should_create_group() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(JpaRepositoryHelper.clusterEntity());

        given(teamJpaRepository.save(any(TeamEntity.class))).willReturn(JpaRepositoryHelper.teamEntity());

        final Group group = defaultGroupRepository.createGroup(group(), cluster().getId());

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_create_group() throws Exception {

        given(clusterJpaRepository.findOne(any(String.class))).willReturn(null);

        defaultGroupRepository.createGroup(group(), cluster().getId());

        fail("How did we get this far?");
    }

    @Test
    public void should_update_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(JpaRepositoryHelper.teamEntity());
        given(teamJpaRepository.save(any(TeamEntity.class))).willReturn(JpaRepositoryHelper.teamEntity());

        final Group group = defaultGroupRepository.updateGroup(group(), cluster().getId());

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
    }

    @Test(expected = NotFoundException.class)
    public void should_not_update_group() throws Exception {

        given(teamJpaRepository.findOne(any(Long.class))).willReturn(null);

        defaultGroupRepository.updateGroup(group(), cluster().getId());

        fail("How did we get this far?");
    }

    @Test
    public void should_delete_group() throws Exception {

        defaultGroupRepository.deleteGroup(group().getId(), cluster().getId());

        verify(teamJpaRepository, times(1)).delete(group().getId().getId());
    }
}