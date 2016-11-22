package org.amhzing.clusterview.infra.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.domain.model.Group;
import org.junit.Test;

import java.util.Set;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.teamEntity;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupFactoryTest {

    @Test
    public void should_convert_team() throws Exception {

        final Set<Group> groups = GroupFactory.convertTeams(ImmutableSet.of(teamEntity()));

        assertThat(groups).isNotEmpty();

        final Group group = groups.iterator().next();
        assertThat(group.getLocation().getCoordX()).isEqualTo(teamEntity().getLocation().getX());
        assertThat(group.getLocation().getCoordY()).isEqualTo(teamEntity().getLocation().getY());
    }
}