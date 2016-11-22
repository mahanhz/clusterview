package org.amhzing.clusterview.web.adapter;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.web.model.GroupModel;
import org.junit.Test;

import java.util.Set;

import static org.amhzing.clusterview.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupModelFactoryTest {

    @Test
    public void should_convert_group() throws Exception {

        final Set<GroupModel> groupModels = GroupModelFactory.convertGroups(ImmutableSet.of(group()));

        assertThat(groupModels).isNotEmpty();

        final GroupModel groupModel = groupModels.iterator().next();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().getCoordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().getCoordY());
    }
}