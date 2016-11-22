package org.amhzing.clusterview.web.adapter;

import org.amhzing.clusterview.domain.model.Group;
import org.junit.Test;

import static org.amhzing.clusterview.helper.ClientModelHelper.groupModel;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupFactoryTest {

    @Test
    public void should_convert_group_model() throws Exception {

        final Group group = GroupFactory.convert(groupModel());

        assertThat(group).isNotNull();
        assertThat(group.getLocation().getCoordX()).isEqualTo(groupModel().getLocation().getCoordX());
        assertThat(group.getLocation().getCoordY()).isEqualTo(groupModel().getLocation().getCoordY());
    }
}