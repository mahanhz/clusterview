package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.adapter.web.Obfuscator;
import org.amhzing.clusterview.appui.helper.ClientModelHelper;
import org.amhzing.clusterview.core.domain.Group;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupFactoryTest {

    @Before
    public void setUp() throws Exception {
        final User user = new User("me@example.com", "Nopass" , emptyList());
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void should_convert_group_model() throws Exception {

        final String obfuscatedId = Obfuscator.obfuscate(1234L);
        final Group group = GroupFactory.convert(ClientModelHelper.groupModel(obfuscatedId));

        assertThat(group).isNotNull();
        assertThat(group.getLocation().coordX()).isEqualTo(ClientModelHelper.groupModel().getLocation().getCoordX());
        assertThat(group.getLocation().coordY()).isEqualTo(ClientModelHelper.groupModel().getLocation().getCoordY());
    }
}