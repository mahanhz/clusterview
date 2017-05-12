package org.amhzing.clusterview.appui.web.adapter;

import org.amhzing.clusterview.core.boundary.enter.UserService;
import org.amhzing.clusterview.core.domain.user.ImmutablePassword;
import org.amhzing.clusterview.core.domain.user.Password;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserAdapterTest {

    @Mock
    private UserService userService;

    private UserAdapter userAdapter;

    @Before
    public void setUp() throws Exception {
        userAdapter = new UserAdapter(userService);
    }

    @Test
    public void should_delegate_to_service() throws Exception {

        final Password password = ImmutablePassword.of("MyNewPassword");
        userAdapter.changePassword(password.value());

        verify(userService, times(1)).changePassword(eq(password));
    }
}