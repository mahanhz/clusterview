package org.amhzing.clusterview.usecase;

import org.amhzing.clusterview.boundary.exit.repository.UserRepository;
import org.amhzing.clusterview.domain.user.ImmutablePassword;
import org.amhzing.clusterview.domain.user.Password;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    private DefaultUserService defaultUserService;

    @Before
    public void setUp() throws Exception {
        defaultUserService = new DefaultUserService(userRepository);
    }

    @Test
    public void should_delegate_to_repository() throws Exception {

        final Password password = ImmutablePassword.of("MyNewPassword");
        defaultUserService.changePassword(password);

        verify(userRepository, times(1)).changePassword(password);
    }

    @Test
    public void should_delegate_users_to_repository() throws Exception {

        defaultUserService.users(1);

        verify(userRepository, times(1)).users(1);
    }
}