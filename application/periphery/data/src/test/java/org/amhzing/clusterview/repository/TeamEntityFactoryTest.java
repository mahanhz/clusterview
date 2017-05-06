package org.amhzing.clusterview.repository;

import org.amhzing.clusterview.helper.JpaRepositoryHelper;
import org.amhzing.clusterview.jpa.entity.TeamEntity;
import org.amhzing.clusterview.jpa.repository.ActivityJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.anotherGroup;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TeamEntityFactoryTest {

    @Mock
    private ActivityJpaRepository activityJpaRepository;

    private TeamEntityFactory teamEntityFactory;

    @Before
    public void setUp() {
        teamEntityFactory = TeamEntityFactory.create(activityJpaRepository);
    }

    @Test
    public void should_convert_group_for_new_team() throws Exception {

        final TeamEntity teamEntity = teamEntityFactory.convertGroupForNewTeam(group(), JpaRepositoryHelper.clusterEntity());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity.getCluster()).isEqualTo(JpaRepositoryHelper.clusterEntity());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(group().getLocation().coordX());
        assertThat(teamEntity.getLocation().getY()).isEqualTo(group().getLocation().coordY());
    }

    @Test
    public void should_convert_group_for_existing_team() throws Exception {

        final TeamEntity teamEntity = teamEntityFactory.convertGroupForExistingTeam(anotherGroup(), JpaRepositoryHelper.teamEntity());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity).isNotEqualTo(JpaRepositoryHelper.teamEntity());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(anotherGroup().getLocation().coordX());
        assertThat(teamEntity.getLocation().getY()).isEqualTo(anotherGroup().getLocation().coordY());
    }
}