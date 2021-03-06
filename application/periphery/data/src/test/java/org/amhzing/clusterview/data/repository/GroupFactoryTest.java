package org.amhzing.clusterview.data.repository;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.core.domain.Group;
import org.amhzing.clusterview.data.helper.JpaRepositoryHelper;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupFactoryTest {

    @Test
    public void should_convert_team() throws Exception {

        final Set<Group> groups = GroupFactory.convertTeams(ImmutableSet.of(JpaRepositoryHelper.teamEntity()));

        assertThat(groups).isNotEmpty();

        final Group group = groups.iterator().next();
        assertThat(group.getLocation().coordX()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getX());
        assertThat(group.getLocation().coordY()).isEqualTo(JpaRepositoryHelper.teamEntity().getLocation().getY());
    }
}