package org.amhzing.clusterview.infra.repository;

import org.amhzing.clusterview.infra.jpa.mapping.TeamEntity;
import org.amhzing.clusterview.infra.jpa.repository.ActivityJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.amhzing.clusterview.helper.DomainModelHelper.anotherGroup;
import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.clusterEntity;
import static org.amhzing.clusterview.helper.JpaRepositoryHelper.teamEntity;
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

        final TeamEntity teamEntity = teamEntityFactory.convertGroupForNewTeam(group(), clusterEntity());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity.getCluster()).isEqualTo(clusterEntity());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(group().getLocation().getCoordX());
        assertThat(teamEntity.getLocation().getY()).isEqualTo(group().getLocation().getCoordY());
    }

    @Test
    public void should_convert_group_for_existing_team() throws Exception {

        final TeamEntity teamEntity = teamEntityFactory.convertGroupForExistingTeam(anotherGroup(), teamEntity());

        assertThat(teamEntity).isNotNull();
        assertThat(teamEntity).isNotEqualTo(teamEntity());
        assertThat(teamEntity.getLocation().getX()).isEqualTo(anotherGroup().getLocation().getCoordX());
        assertThat(teamEntity.getLocation().getY()).isEqualTo(anotherGroup().getLocation().getCoordY());
    }
}