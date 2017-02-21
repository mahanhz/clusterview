package org.amhzing.clusterview.app.web.adapter;

import com.google.common.collect.ImmutableSet;
import org.amhzing.clusterview.app.web.model.GroupModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

import static java.util.Collections.emptyList;
import static org.amhzing.clusterview.app.helper.DomainModelHelper.group;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupModelFactoryTest {

    @Before
    public void setUp() throws Exception {
        final User user = new User("me@example.com", "Nopass" , emptyList());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    @Test
    public void should_convert_group() throws Exception {

        final Set<GroupModel> groupModels = GroupModelFactory.convertGroups(ImmutableSet.of(group()));

        assertThat(groupModels).isNotEmpty();

        final GroupModel groupModel = groupModels.iterator().next();
        assertThat(groupModel.getLocation().getCoordX()).isEqualTo(group().getLocation().coordX());
        assertThat(groupModel.getLocation().getCoordY()).isEqualTo(group().getLocation().coordY());
    }
}