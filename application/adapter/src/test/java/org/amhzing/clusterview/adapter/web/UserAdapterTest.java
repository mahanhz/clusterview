package org.amhzing.clusterview.adapter.web;

import com.google.common.collect.ImmutableList;
import org.amhzing.clusterview.adapter.web.api.user.UserDTO;
import org.amhzing.clusterview.adapter.web.api.user.UsersDTO;
import org.amhzing.clusterview.core.boundary.enter.UserService;
import org.amhzing.clusterview.core.domain.user.Page;
import org.amhzing.clusterview.core.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.amhzing.clusterview.adapter.web.helper.UserDomainModelHelper.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserAdapterTest {

    public static final int TOTAL_PAGES = 2;
    public static final int PAGE_NUMBER = 1;
    public static final String EMAIL = "me@example.com";

    @Mock
    private UserService userService;

    private UserAdapter userAdapter;

    @Before
    public void setUp() throws Exception {
        userAdapter = new UserAdapter(userService);
    }

    @Test
    public void pagedUsers() throws Exception {
        given(userService.users(PAGE_NUMBER - 1)).willReturn(Page.create(userList(), TOTAL_PAGES));
        final Page<User> pagedUsers = userAdapter.pagedUsers(PAGE_NUMBER);

        assertThat(pagedUsers.getContent()).isEqualTo(userList());
        assertThat(pagedUsers.getTotalPages()).isEqualTo(TOTAL_PAGES);
    }

    @Test
    public void users() throws Exception {
        given(userService.users(PAGE_NUMBER - 1)).willReturn(Page.create(userList(), TOTAL_PAGES));

        final UsersDTO users = userAdapter.users(PAGE_NUMBER);

        assertThat(users.users).isNotEmpty();

        final UserDTO userDTO = users.users.get(0);
        assertThat(userDTO.email).isEqualTo(EMAIL);
    }

    private ImmutableList<User> userList() {
        return ImmutableList.of(user(EMAIL));
    }
}